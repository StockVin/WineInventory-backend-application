package com.wineinventory.paymentandsubscriptions.application.internal.commandservices;

import com.wineinventory.paymentandsubscriptions.domain.model.aggregates.Subscription;
import com.wineinventory.paymentandsubscriptions.domain.model.commands.CreateSubscriptionCommand;
import com.wineinventory.paymentandsubscriptions.domain.model.commands.CancelSubscriptionCommand;
import com.wineinventory.paymentandsubscriptions.domain.model.commands.WebhookPaymentCommand;
import com.wineinventory.paymentandsubscriptions.domain.repositories.SubscriptionRepository;
import com.wineinventory.paymentandsubscriptions.infrastructure.external.paypal.PayPalSubscriptionService;
import com.wineinventory.paymentandsubscriptions.domain.model.queries.PayPalCreateSubscriptionQuery;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SubscriptionCommandServiceImpl implements SubscriptionCommandService {

    private final SubscriptionRepository subscriptionRepository;
    private final PayPalSubscriptionService payPalSubscriptionService;

    public SubscriptionCommandServiceImpl(
            SubscriptionRepository subscriptionRepository,
            PayPalSubscriptionService payPalSubscriptionService) {
        this.subscriptionRepository = subscriptionRepository;
        this.payPalSubscriptionService = payPalSubscriptionService;
    }

    private String extractApprovalLink(PayPalCreateSubscriptionQuery response) {
        return response.links().stream()
                .filter(link -> link.rel().equals("approve"))
                .findFirst()
                .map(PayPalCreateSubscriptionQuery.PayPalLink::href)
                .orElse(null);
    }

    @Override
    public Subscription handle(CreateSubscriptionCommand command) {

        PayPalCreateSubscriptionQuery response =
                payPalSubscriptionService.createSubscription(command.paypalPlanId());

        String paypalSubscriptionId = response.id();
        String status = response.status();
        String approvalLink = extractApprovalLink(response);
        Subscription subscription = new Subscription(
                command.userId(),
                command.planId(),
                paypalSubscriptionId,
                status,
                command.currency(),
                command.amount(),
                LocalDateTime.now(),
                null
        );
        subscriptionRepository.save(subscription);

        return subscription;
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
