package com.wineinventory.planmanagement.interfaces.rest.resources;

public record PlanResource(
        Long id,
        String code,
        String name,
        String description,
        Double price,
        String currency,
        String paypalPlanId
) {}
