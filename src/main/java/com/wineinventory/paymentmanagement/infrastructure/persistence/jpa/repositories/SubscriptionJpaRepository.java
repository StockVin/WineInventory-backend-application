package com.wineinventory.paymentmanagement.infrastructure.persistence.jpa.repositories;

import com.wineinventory.paymentmanagement.domain.model.aggregates.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionJpaRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByPaypalSubscriptionId(String paypalSubscriptionId);

    List<Subscription> findByUserId(Long userId);
}
