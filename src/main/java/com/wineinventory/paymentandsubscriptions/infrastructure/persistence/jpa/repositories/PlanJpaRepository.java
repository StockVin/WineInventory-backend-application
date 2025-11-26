package com.wineinventory.paymentandsubscriptions.infrastructure.persistence.jpa.repositories;

import com.wineinventory.paymentandsubscriptions.domain.model.entities.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanJpaRepository extends JpaRepository<Plan, Long> {

    Optional<Plan> findByPlanId(String planId);

    Optional<Plan> findByPlanType(String planType);
}
