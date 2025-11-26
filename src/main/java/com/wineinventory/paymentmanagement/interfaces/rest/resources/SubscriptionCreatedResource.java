package com.wineinventory.paymentmanagement.interfaces.rest.resources;

public record SubscriptionCreatedResource(
        Long localSubscriptionId,
        String paypalSubscriptionId,
        String status,
        String approvalLink
) {}
