package com.wineinventory.paymentandsubscriptions.infrastructure.external.paypal;

import com.wineinventory.paymentandsubscriptions.domain.model.commands.PayPalWebhookVerificationCommand;
import com.wineinventory.paymentandsubscriptions.domain.model.queries.PayPalWebhookVerificationQuery;
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

        PayPalWebhookVerificationCommand request = new PayPalWebhookVerificationCommand(
                headers.getFirst("paypal-auth-algo"),
                headers.getFirst("paypal-cert-url"),
                headers.getFirst("paypal-transmission-id"),
                headers.getFirst("paypal-transmission-sig"),
                headers.getFirst("paypal-transmission-time"),
                webhookId,
                eventBody
        );

        HttpHeaders customHeaders = new HttpHeaders();
        ResponseEntity<PayPalWebhookVerificationQuery> response =
                payPalClient.postWithHeaders(
                        "/v1/notifications/verify-webhook-signature",
                        request,
                        PayPalWebhookVerificationQuery.class,
                        customHeaders
                );

        return response.getBody() != null &&
                response.getBody().verification_status().equals("SUCCESS");
    }
}
