package com.wineinventory.profilemanagement.infrastructure.persistence.jpa.repositories;

import com.wineinventory.profilemanagement.domain.model.aggregates.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileJpaRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUsername(String username);
    Optional<Profile> findByEmail(String email);
}
