package com.wineinventory.reportingandcareguide.infrastructure.persistence.jpa.repositories;

import com.wineinventory.reportingandcareguide.domain.model.entities.CareGuide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CareGuideRepository extends JpaRepository<CareGuide, Long> {
    Optional<CareGuide> findById(Long id);
    List<CareGuide> findByTypeAndDescription(String type, String description);
    List<CareGuide> findByAccountId(String accountId);
}