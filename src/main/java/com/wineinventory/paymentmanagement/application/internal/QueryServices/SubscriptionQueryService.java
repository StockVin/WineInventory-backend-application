package com.wineinventory.paymentmanagement.application.internal.QueryServices;

import com.wineinventory.paymentmanagement.domain.model.aggregates.Subscription;

import java.util.List;

public interface SubscriptionQueryService {

    Subscription handle(Long subscriptionId);

    List<Subscription> handleFindByUser(Long userId);
}
