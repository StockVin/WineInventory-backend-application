package com.wineinventory.paymentandsubscriptions.domain.model.queries;

import java.util.List;

public record PayPalCreateSubscriptionQuery(
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
