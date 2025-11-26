package com.wineinventory.paymentmanagement.external.paypal;

import com.wineinventory.paymentmanagement.external.paypal.dtos.PayPalWebhookVerificationRequest;
import com.wineinventory.paymentmanagement.external.paypal.dtos.PayPalWebhookVerificationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PayPalWebhookValidator {

    @Value("${paypal.webhook.id}")
    private String webhookId;

    private final PayPalClient payPalClient;

    public PayPalWebhookValidator(PayPalClient payPalClient) {
        this.payPalClient = payPalClient;
    }

    public boolean isValid(
            HttpHeaders headers,
            Map<String, Object> eventBody
    ) {

        PayPalWebhookVerificationRequest request = new PayPalWebhookVerificationRequest(
                headers.getFirst("paypal-auth-algo"),
                headers.getFirst("paypal-cert-url"),
                headers.getFirst("paypal-transmission-id"),
                headers.getFirst("paypal-transmission-sig"),
                headers.getFirst("paypal-transmission-time"),
                webhookId,
                eventBody
        );

        HttpHeaders customHeaders = new HttpHeaders();
        ResponseEntity<PayPalWebhookVerificationResponse> response =
                payPalClient.postWithHeaders(
                        "/v1/notifications/verify-webhook-signature",
                        request,
                        PayPalWebhookVerificationResponse.class,
                        customHeaders
                );

        return response.getBody() != null &&
                response.getBody().verification_status().equals("SUCCESS");
    }
}
