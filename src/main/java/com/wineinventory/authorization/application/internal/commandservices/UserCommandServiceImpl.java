package com.wineinventory.authorization.application.internal.commandservices;

import com.wineinventory.authorization.application.internal.outboundservices.hashing.HashingService;
import com.wineinventory.authorization.application.internal.outboundservices.tokens.TokenService;
import com.wineinventory.authorization.domain.model.aggregates.User;
import com.wineinventory.authorization.domain.model.commands.SignInCommand;
import com.wineinventory.authorization.domain.model.commands.SignUpCommand;
import com.wineinventory.authorization.domain.services.UserCommandService;
import com.wineinventory.authorization.infrastructure.persistence.jpa.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;

    public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService, TokenService tokenService) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
    }

    @Override
    public Optional<ImmutableTriple<User, String, Long>> handle(SignInCommand command) {
        var user = userRepository.findByUsername(command.username());
        if (user.isEmpty())
            throw new RuntimeException("User not found");
        if (!hashingService.matches(command.password(), user.get().getPassword()))
            throw new RuntimeException("Invalid password");

        var token = tokenService.generateToken(user.get().getUsername());

        return Optional.of(ImmutableTriple.of(user.get(), token, user.get().getUserId()));
    }

    @Override
    public Optional<User> handle(SignUpCommand command) {
        if (userRepository.existsByUsername(command.username()))
            throw new RuntimeException("Username already exists");
        var user = new User(
                command.username(),
                command.email(),
                hashingService.encode(command.password()),
                hashingService.encode(command.validationPassword()),
                command.role()
        );
        userRepository.save(user);
        return userRepository.findByUsername(command.username());
    }
}