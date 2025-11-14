package com.wineinventory.profilemanagement.infrastructure.persistence.jpa.repositories;

import com.wineinventory.profilemanagement.domain.model.aggregates.Profile;
import com.wineinventory.profilemanagement.domain.repositories.ProfileRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProfileRepositoryImpl implements ProfileRepository {

    private final ProfileJpaRepository profileJpaRepository;

    public ProfileRepositoryImpl(ProfileJpaRepository profileJpaRepository) {
        this.profileJpaRepository = profileJpaRepository;
    }

    @Override
    public Optional<Profile> findByUsername(String username) {
        return profileJpaRepository.findByUsername(username);
    }

    @Override
    public Optional<Profile> findByEmail(String email) {
        return profileJpaRepository.findByEmail(email);
    }

    @Override
    public Profile save(Profile profile) {
        return profileJpaRepository.save(profile);
    }
}
