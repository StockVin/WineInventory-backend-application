package com.wineinventory.paymentandsubscriptions.domain.repositories;

import com.wineinventory.paymentandsubscriptions.domain.model.entities.Plan;

import java.util.List;
import java.util.Optional;

public interface PlanRepository {

    Plan save(Plan plan);

    Optional<Plan> findById(Long id);

    Optional<Plan> findByPlanId(String planId);

    Optional<Plan> findByPlanType(String planType);

    List<Plan> findAll();
}
