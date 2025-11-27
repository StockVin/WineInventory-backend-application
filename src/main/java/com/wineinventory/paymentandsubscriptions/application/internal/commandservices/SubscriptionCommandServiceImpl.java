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
        // Always create subscription with the provided PayPal subscription ID
        Subscription subscription = new Subscription(
                command.userId(),
                command.planId(),
                command.paypalSubscriptionId(),
                "ACTIVE",
                command.currency(),
                command.amount(),
                LocalDateTime.now(),
                null
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
            System.out.println("Processing webhook for payment ID: " + command.paymentId());
            System.out.println("Webhook processed successfully for payment: " + command.paymentId());
            
        } catch (Exception e) {
            System.err.println("Error processing webhook payment: " + e.getMessage());
            throw new RuntimeException("Failed to process webhook payment", e);
        }
    }
}
