package com.wineinventory.planmanagement.infrastructure.persistence.jpa.repositories;

import com.wineinventory.planmanagement.domain.model.aggregates.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanJpaRepository extends JpaRepository<Plan, Long> {

    Optional<Plan> findByCode(String code);
}
