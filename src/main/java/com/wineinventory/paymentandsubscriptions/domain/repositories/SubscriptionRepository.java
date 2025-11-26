package com.wineinventory.paymentandsubscriptions.domain.repositories;

import com.wineinventory.paymentandsubscriptions.domain.model.aggregates.Subscription;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository {

    Subscription save(Subscription subscription);

    Optional<Subscription> findById(Long id);

    Optional<Subscription> findByPaypalSubscriptionId(String paypalSubscriptionId);

    List<Subscription> findByUserId(Long userId);

    List<Subscription> findByNextBillingDateBeforeAndStatus(LocalDateTime date, String status);

    long countByStatus(String status);
}
