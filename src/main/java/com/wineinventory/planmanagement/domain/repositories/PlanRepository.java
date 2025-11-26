package com.wineinventory.planmanagement.domain.repositories;

import com.wineinventory.planmanagement.domain.model.aggregates.Plan;

import java.util.List;
import java.util.Optional;

public interface PlanRepository {

    Plan save(Plan plan);

    Optional<Plan> findById(Long id);

    Optional<Plan> findByCode(String code);

    List<Plan> findAll();
}
