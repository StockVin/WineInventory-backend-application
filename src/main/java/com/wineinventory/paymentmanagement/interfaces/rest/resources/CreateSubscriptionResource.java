package com.wineinventory.paymentmanagement.interfaces.rest.resources;

public record CreateSubscriptionResource(
        Long userId,
        Long planId,
        String paypalPlanId,
        String currency,
        Double amount
) {}
