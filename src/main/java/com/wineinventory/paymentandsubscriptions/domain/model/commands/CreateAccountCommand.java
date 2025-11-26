package com.wineinventory.paymentandsubscriptions.domain.model.commands;

public record CreateAccountCommand(
    String businessId,
    String role
) {}
