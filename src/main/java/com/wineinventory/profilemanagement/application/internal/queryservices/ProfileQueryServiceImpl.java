package com.wineinventory.profilemanagement.application.internal.queryservices;

import com.wineinventory.authorization.domain.model.aggregates.User;
import com.wineinventory.authorization.infrastructure.persistence.jpa.repositories.UserRepository;
import com.wineinventory.profilemanagement.domain.services.ProfileQueryService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileQueryServiceImpl implements ProfileQueryService {

    private final UserRepository userRepository;

    public ProfileQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
