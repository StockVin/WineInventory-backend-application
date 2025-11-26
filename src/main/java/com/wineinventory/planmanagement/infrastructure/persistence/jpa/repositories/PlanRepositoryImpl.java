package com.wineinventory.planmanagement.infrastructure.persistence.jpa.repositories;

import com.wineinventory.planmanagement.domain.model.aggregates.Plan;
import com.wineinventory.planmanagement.domain.repositories.PlanRepository;
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
    public Optional<Plan> findByCode(String code) {
        return jpaRepository.findByCode(code);
    }

    @Override
    public List<Plan> findAll() {
        return jpaRepository.findAll();
    }
}
