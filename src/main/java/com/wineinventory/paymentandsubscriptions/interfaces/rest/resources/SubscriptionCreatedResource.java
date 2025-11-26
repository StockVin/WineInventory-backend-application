package com.wineinventory.paymentandsubscriptions.interfaces.rest.resources;

public record SubscriptionCreatedResource(
        Long localSubscriptionId,
        String paypalSubscriptionId,
        String status,
        String approvalLink
) {}
