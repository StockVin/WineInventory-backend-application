package com.wineinventory.profilemanagement.domain.repositories;

import com.wineinventory.profilemanagement.domain.model.aggregates.Profile;

import java.util.Optional;

public interface ProfileRepository {
    Optional<Profile> findByUsername(String username);
    Optional<Profile> findByEmail(String email);
    Profile save(Profile profile);
}
