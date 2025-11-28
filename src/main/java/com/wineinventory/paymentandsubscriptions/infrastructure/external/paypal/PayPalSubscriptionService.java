package com.wineinventory.paymentandsubscriptions.infrastructure.external.paypal;

import com.wineinventory.paymentandsubscriptions.domain.model.commands.PayPalApplicationCommnad;
import com.wineinventory.paymentandsubscriptions.domain.model.commands.PayPalCreateSubscriptionCommand;
import com.wineinventory.paymentandsubscriptions.domain.model.queries.PayPalCreateSubscriptionQuery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;

@Service
public class PayPalSubscriptionService {

    private final PayPalClient payPalClient;

    @Value("${paypal.return-url}")
    private String returnUrl;

    @Value("${paypal.cancel-url}")
    private String cancelUrl;

    public PayPalSubscriptionService(PayPalClient payPalClient) {
        this.payPalClient = payPalClient;
    }

    public PayPalCreateSubscriptionQuery createSubscription(String paypalPlanId) {

        PayPalApplicationCommnad context = new PayPalApplicationCommnad(
                "WineInventory",
                "en-US",
                "SUBSCRIBE_NOW",
                returnUrl,
                cancelUrl
        );

        PayPalCreateSubscriptionCommand request = new PayPalCreateSubscriptionCommand(
                paypalPlanId,
                null,
                context
        );

        ResponseEntity<PayPalCreateSubscriptionQuery> response =
                payPalClient.post("/v1/billing/subscriptions", request, PayPalCreateSubscriptionQuery.class);

        return response.getBody();
    }

    public PayPalCreateSubscriptionQuery createSubscription(String paypalPlanId, String idempotencyKey) {
        PayPalApplicationCommnad context = new PayPalApplicationCommnad(
                "WineInventory",
                "en-US",
                "SUBSCRIBE_NOW",
                returnUrl,
                cancelUrl
        );

        PayPalCreateSubscriptionCommand request = new PayPalCreateSubscriptionCommand(
                paypalPlanId,
                null,
                context
        );

        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            return createSubscription(paypalPlanId);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("PayPal-Request-Id", idempotencyKey);

        ResponseEntity<PayPalCreateSubscriptionQuery> response =
                payPalClient.postWithHeaders("/v1/billing/subscriptions", request, PayPalCreateSubscriptionQuery.class, headers);
        return response.getBody();
    }
}
