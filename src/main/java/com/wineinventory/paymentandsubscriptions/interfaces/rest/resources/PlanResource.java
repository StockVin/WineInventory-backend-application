package com.wineinventory.paymentandsubscriptions.interfaces.rest.resources;

public record PlanResource(
        String planId,
        String planType,
        String description,
        String paymentFrequency,
        Double price,
        String currency,
        Integer maxProducts
) {}
