package com.wineinventory.planmanagement.interfaces.rest.resources;

public record CreatePlanResource(
        String code,
        String name,
        String description,
        Double price,
        String currency,
        String paypalPlanId
) {}
