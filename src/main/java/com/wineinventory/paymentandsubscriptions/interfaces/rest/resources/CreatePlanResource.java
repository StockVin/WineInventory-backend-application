package com.wineinventory.paymentandsubscriptions.interfaces.rest.resources;

public record CreatePlanResource(
        String code,
        String name,
        String description,
        Double price,
        String currency,
        String paypalPlanId,
        String paypalSubscriptionId
) {}
