package com.wineinventory.paymentmanagement.external.paypal;

import com.wineinventory.paymentmanagement.external.paypal.dtos.PayPalApplicationContext;
import com.wineinventory.paymentmanagement.external.paypal.dtos.PayPalCreateSubscriptionRequest;
import com.wineinventory.paymentmanagement.external.paypal.dtos.PayPalCreateSubscriptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PayPalSubscriptionService {

    private final PayPalClient payPalClient;

    public PayPalSubscriptionService(PayPalClient payPalClient) {
        this.payPalClient = payPalClient;
    }

    public PayPalCreateSubscriptionResponse createSubscription(String paypalPlanId) {

        // Puedes cambiar return_url y cancel_url por las reales de tu front
        PayPalApplicationContext context = new PayPalApplicationContext(
                "WineInventory",
                "en-US",
                "SUBSCRIBE_NOW",
                "https://example.com/paypal/success",
                "https://example.com/paypal/cancel"
        );

        PayPalCreateSubscriptionRequest request = new PayPalCreateSubscriptionRequest(
                paypalPlanId,
                null,
                context
        );

        ResponseEntity<PayPalCreateSubscriptionResponse> response =
                payPalClient.post("/v1/billing/subscriptions", request, PayPalCreateSubscriptionResponse.class);

        return response.getBody();
    }
}
