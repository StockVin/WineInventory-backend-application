package com.wineinventory.paymentandsubscriptions.interfaces.rest.resources;

import java.time.LocalDateTime;

public record SubscriptionResource(
        Long id,
        Long userId,
        Long planId,
        String paypalSubscriptionId,
        String status,
        String currency,
        Double amount,
        LocalDateTime startDate,
        LocalDateTime nextBillingDate
) {}
