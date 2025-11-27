package com.wineinventory.paymentandsubscriptions.interfaces.rest.resources;

public record PlanResource(
        String planId,
        String paypalPlanId,
        String paypalSubscriptionId,
        String planType,
        String description,
        String paymentFrequency,
        Double price,
        String currency,
        Integer maxProducts
) {}
