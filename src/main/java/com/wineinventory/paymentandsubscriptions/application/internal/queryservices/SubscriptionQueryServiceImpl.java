package com.wineinventory.paymentandsubscriptions.application.internal.queryservices;

import com.wineinventory.paymentandsubscriptions.domain.model.aggregates.Subscription;
import com.wineinventory.paymentandsubscriptions.domain.model.queries.GetPlanProductsLimitByAccountIdQuery;
import com.wineinventory.paymentandsubscriptions.domain.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionQueryServiceImpl implements SubscriptionQueryService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionQueryServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public Subscription handle(Long subscriptionId) {
        return subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found"));
    }

    @Override
    public List<Subscription> handleFindByUser(Long userId) {
        return subscriptionRepository.findByUserId(userId);
    }

    @Override
    public Integer handle(GetPlanProductsLimitByAccountIdQuery query) {
        return 500;
    }
}
