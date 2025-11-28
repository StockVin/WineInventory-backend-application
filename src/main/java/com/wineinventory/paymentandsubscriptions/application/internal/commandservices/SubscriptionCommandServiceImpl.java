package com.wineinventory.paymentandsubscriptions.application.internal.commandservices;

import com.wineinventory.paymentandsubscriptions.domain.model.aggregates.Subscription;
import com.wineinventory.paymentandsubscriptions.domain.model.commands.CreateSubscriptionCommand;
import com.wineinventory.paymentandsubscriptions.domain.model.commands.CancelSubscriptionCommand;
import com.wineinventory.paymentandsubscriptions.domain.model.commands.WebhookPaymentCommand;
import com.wineinventory.paymentandsubscriptions.domain.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SubscriptionCommandServiceImpl implements SubscriptionCommandService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionCommandServiceImpl(
            SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public Subscription handle(CreateSubscriptionCommand command) {
        Subscription subscription = new Subscription(
                command.userId(),
                command.planId(),
                command.paypalSubscriptionId(),
                command.status(),
                command.currency(),
                command.amount(),
                LocalDateTime.now(),
                null,
                command.approvalUrl()
        );
        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription handle(CancelSubscriptionCommand command) {
        return subscriptionRepository.findById(command.subscriptionId())
                .map(subscription -> {
                    subscription.cancel();
                    return subscriptionRepository.save(subscription);
                })
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found"));
    }
    
    @Override
    public void handle(WebhookPaymentCommand command) {
        try {
            String paypalSubscriptionId = command.paymentId();
            if (paypalSubscriptionId == null || paypalSubscriptionId.isBlank()) return;

            var matches = subscriptionRepository.findByPaypalSubscriptionId(paypalSubscriptionId);
            if (matches == null || matches.isEmpty()) {
                System.out.println("Webhook received for unknown subscription: " + paypalSubscriptionId);
                return;
            }

            for (Subscription subscription : matches) {
                subscription.setStatus("ACTIVE");
                if (subscription.getNextBillingDate() == null) {
                    subscription.setNextBillingDate(LocalDateTime.now().plusMonths(1));
                }
                subscriptionRepository.save(subscription);
            }
            System.out.println("Webhook processed and subscriptions updated for: " + paypalSubscriptionId);
        } catch (Exception e) {
            System.err.println("Error processing webhook payment: " + e.getMessage());
            throw new RuntimeException("Failed to process webhook payment", e);
        }
    }
}
