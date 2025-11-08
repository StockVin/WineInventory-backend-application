package com.wineinventory.ProfileManagement.Domain.Services;

import com.wineinventory.Authorization.Domain.Model.Aggregates.User;

import java.util.Optional;

public interface ProfileQueryService {
    Optional<User> findByUsername(String username);
}
