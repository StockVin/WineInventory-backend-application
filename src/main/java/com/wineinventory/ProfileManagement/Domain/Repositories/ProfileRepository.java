package com.wineinventory.ProfileManagement.Domain.Repositories;

import com.wineinventory.ProfileManagement.Domain.Model.Aggregates.Profile;

import java.util.Optional;

public interface ProfileRepository {
    Optional<Profile> findByUsername(String username);
    Optional<Profile> findByEmail(String email);
    Profile save(Profile profile);
}
