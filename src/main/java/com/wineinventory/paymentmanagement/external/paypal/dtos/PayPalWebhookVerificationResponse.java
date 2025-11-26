package com.wineinventory.paymentmanagement.external.paypal.dtos;

public record PayPalWebhookVerificationResponse(
        String verification_status
) {}
