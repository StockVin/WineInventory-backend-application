package com.wineinventory.paymentmanagement.interfaces.rest.assemblers;

import com.wineinventory.paymentmanagement.domain.model.aggregates.Subscription;
import com.wineinventory.paymentmanagement.interfaces.rest.resources.SubscriptionResource;

public class SubscriptionResourceAssembler {

    public static SubscriptionResource toResource(Subscription subscription) {
        return new SubscriptionResource(
                subscription.getId(),
                subscription.getUserId(),
                subscription.getPlanId(),
                subscription.getPaypalSubscriptionId(),
                subscription.getStatus(),
                subscription.getCurrency(),
                subscription.getAmount(),
                subscription.getStartDate(),
                subscription.getNextBillingDate()
        );
    }
}
