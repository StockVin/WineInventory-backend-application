package com.wineinventory.profilemanagement.domain.services;

import com.wineinventory.authorization.domain.model.aggregates.User;

import java.util.Optional;

public interface ProfileQueryService {
    Optional<User> findByUsername(String username);
}
