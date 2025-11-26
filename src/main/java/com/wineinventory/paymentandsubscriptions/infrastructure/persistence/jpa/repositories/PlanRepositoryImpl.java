package com.wineinventory.paymentandsubscriptions.infrastructure.persistence.jpa.repositories;

import com.wineinventory.paymentandsubscriptions.domain.model.entities.Plan;
import com.wineinventory.paymentandsubscriptions.domain.repositories.PlanRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PlanRepositoryImpl implements PlanRepository {

    private final PlanJpaRepository jpaRepository;

    public PlanRepositoryImpl(PlanJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Plan save(Plan plan) {
        return jpaRepository.save(plan);
    }

    @Override
    public Optional<Plan> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<Plan> findByPlanId(String planId) {
        return jpaRepository.findByPlanId(planId);
    }

    @Override
    public Optional<Plan> findByPlanType(String planType) {
        return jpaRepository.findByPlanType(planType);
    }

    @Override
    public List<Plan> findAll() {
        return jpaRepository.findAll();
    }
}
