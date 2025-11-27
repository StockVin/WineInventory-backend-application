package com.wineinventory.paymentandsubscriptions.infrastructure.persistence.jpa.repositories;

import com.wineinventory.paymentandsubscriptions.domain.model.aggregates.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionJpaRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByPaypalSubscriptionId(String paypalSubscriptionId);

    List<Subscription> findByUserId(Long userId);

    List<Subscription> findByNextBillingDateBeforeAndStatus(LocalDateTime date, String status);

    @Query("SELECT COUNT(s) FROM Subscription s WHERE s.status = :status")
    long countByStatus(@Param("status") String status);
}
