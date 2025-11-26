package com.wineinventory.paymentmanagement.external.paypal.dtos;

import java.util.Map;

public record PayPalWebhookVerificationRequest(
        String auth_algo,
        String cert_url,
        String transmission_id,
        String transmission_sig,
        String transmission_time,
        String webhook_id,
        Map<String, Object> webhook_event
) {}
