package com.wineinventory.paymentmanagement.external.paypal.dtos;

public record PayPalPlanRequest(
        String productId,
        String name,
        String description,
        String billingCycles,
        String paymentPreferences
) {}
