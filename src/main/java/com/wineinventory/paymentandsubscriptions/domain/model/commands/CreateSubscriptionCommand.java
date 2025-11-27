package com.wineinventory.paymentandsubscriptions.domain.model.commands;

public record CreateSubscriptionCommand(
        Long userId,
        Long planId,
        String paypalSubscriptionId,
        String currency,
        Double amount
) {}
