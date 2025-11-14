package com.wineinventory.profilemanagement.application.internal.commandservices;

import com.wineinventory.authorization.application.internal.outboundservices.hashing.HashingService;
import com.wineinventory.authorization.domain.model.aggregates.User;
import com.wineinventory.authorization.infrastructure.persistence.jpa.repositories.UserRepository;
import com.wineinventory.profilemanagement.domain.model.commands.UpdateProfileCommand;
import com.wineinventory.profilemanagement.domain.services.ProfileCommandService;
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