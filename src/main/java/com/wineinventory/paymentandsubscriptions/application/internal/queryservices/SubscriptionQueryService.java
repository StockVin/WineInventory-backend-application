package com.wineinventory.paymentandsubscriptions.application.internal.queryservices;

import com.wineinventory.paymentandsubscriptions.domain.model.aggregates.Subscription;
import com.wineinventory.paymentandsubscriptions.domain.model.queries.GetPlanProductsLimitByAccountIdQuery;

import java.util.List;

public interface SubscriptionQueryService {

    Subscription handle(Long subscriptionId);

    List<Subscription> handleFindByUser(Long userId);

    Integer handle(GetPlanProductsLimitByAccountIdQuery query);
}
