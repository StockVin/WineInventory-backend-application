package com.wineinventory.paymentmanagement.external.paypal.dtos;

import java.util.List;

public record PayPalCreateSubscriptionResponse(
        String id,
        String status,
        List<PayPalLink> links
) {

    public record PayPalLink(
            String href,
            String rel,
            String method
    ) {}
}
