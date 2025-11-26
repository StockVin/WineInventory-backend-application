package com.wineinventory.paymentmanagement.infrastructure.persistence.jpa.repositories;

import com.wineinventory.paymentmanagement.domain.model.aggregates.Subscription;
import com.wineinventory.paymentmanagement.domain.repositories.SubscriptionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

    private final SubscriptionJpaRepository jpaRepository;

    public SubscriptionRepositoryImpl(SubscriptionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Subscription save(Subscription subscription) {
        return jpaRepository.save(subscription);
    }

    @Override
    public Optional<Subscription> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<Subscription> findByPaypalSubscriptionId(String paypalSubscriptionId) {
        return jpaRepository.findByPaypalSubscriptionId(paypalSubscriptionId);
    }

    @Override
    public List<Subscription> findByUserId(Long userId) {
        return jpaRepository.findByUserId(userId);
    }
}
