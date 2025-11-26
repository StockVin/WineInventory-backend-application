package com.wineinventory.paymentandsubscriptions.domain.model.commands;

public record PayPalPlanCommand(
        String productId,
        String name,
        String description,
        String billingCycles,
        String paymentPreferences
) {}
