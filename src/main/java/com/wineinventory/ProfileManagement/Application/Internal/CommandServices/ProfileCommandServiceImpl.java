package com.wineinventory.ProfileManagement.Application.Internal.CommandServices;

import com.wineinventory.Authorization.Application.Internal.OutboundServices.Hashing.HashingService;
import com.wineinventory.Authorization.Domain.Model.Aggregates.User;
import com.wineinventory.Authorization.Infrastructure.Persistence.JPA.Repositories.UserRepository;
import com.wineinventory.ProfileManagement.Domain.Model.Commands.UpdateProfileCommand;
import com.wineinventory.ProfileManagement.Domain.Services.ProfileCommandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProfileCommandServiceImpl implements ProfileCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;

    public ProfileCommandServiceImpl(UserRepository userRepository, HashingService hashingService) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
    }

    @Override
    public User handle(UpdateProfileCommand command) {
        var user = userRepository.findByUsername(command.currentUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (command.username() != null) {
            user.setUsername(command.username());
        }

        if (command.email() != null) {
            user.setEmail(command.email());
        }

        if (command.password() != null) {
            user.setPassword(hashingService.encode(command.password()));
        }

        if (command.validationPassword() != null) {
            user.setValidationPassword(hashingService.encode(command.validationPassword()));
        }

        if (command.role() != null) {
            user.setRole(command.role());
        }

        return userRepository.save(user);
    }
}