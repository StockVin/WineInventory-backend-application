package com.wineinventory.paymentandsubscriptions.domain.model.queries;

public record PayPalWebhookVerificationQuery(
        String verification_status
) {}
