package com.wineinventory.ProfileManagement.Application.Internal.QueryServices;

import com.wineinventory.Authorization.Domain.Model.Aggregates.User;
import com.wineinventory.Authorization.Infrastructure.Persistence.JPA.Repositories.UserRepository;
import com.wineinventory.ProfileManagement.Domain.Services.ProfileQueryService;
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
