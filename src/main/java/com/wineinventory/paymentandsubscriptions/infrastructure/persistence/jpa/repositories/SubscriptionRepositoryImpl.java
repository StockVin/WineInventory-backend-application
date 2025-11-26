package com.wineinventory.paymentandsubscriptions.infrastructure.persistence.jpa.repositories;

import com.wineinventory.paymentandsubscriptions.domain.model.aggregates.Subscription;
import com.wineinventory.paymentandsubscriptions.domain.repositories.SubscriptionRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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

    @Override
    public List<Subscription> findByNextBillingDateBeforeAndStatus(LocalDateTime date, String status) {
        return jpaRepository.findByNextBillingDateBeforeAndStatus(date, status);
    }

    @Override
    public long countByStatus(String status) {
        return jpaRepository.countByStatus(status);
    }
}
