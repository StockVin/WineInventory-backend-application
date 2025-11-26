package com.wineinventory.paymentmanagement.domain.repositories;

import com.wineinventory.paymentmanagement.domain.model.aggregates.Subscription;
import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository {

    Subscription save(Subscription subscription);

    Optional<Subscription> findById(Long id);

    Optional<Subscription> findByPaypalSubscriptionId(String paypalSubscriptionId);

    List<Subscription> findByUserId(Long userId);
}
