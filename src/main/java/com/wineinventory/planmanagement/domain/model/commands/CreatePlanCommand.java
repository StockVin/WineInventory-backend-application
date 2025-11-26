package com.wineinventory.planmanagement.domain.model.commands;

public record CreatePlanCommand(
        String code,
        String name,
        String description,
        Double price,
        String currency,
        String paypalPlanId
) {}
