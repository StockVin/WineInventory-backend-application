package com.wineinventory.paymentandsubscriptions.domain.model.commands;

public record CancelSubscriptionCommand(
        Long subscriptionId
) {}
