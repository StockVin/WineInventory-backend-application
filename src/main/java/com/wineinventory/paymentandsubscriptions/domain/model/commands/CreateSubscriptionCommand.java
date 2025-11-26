package com.wineinventory.paymentandsubscriptions.domain.model.commands;

public record CreateSubscriptionCommand(
        Long userId,
        Long planId,
        String paypalPlanId,
        String currency,
        Double amount
) {}
