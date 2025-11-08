package com.wineinventory.Authorization.Domain.Services;

import com.wineinventory.Authorization.Domain.Model.Aggregates.User;
import com.wineinventory.Authorization.Domain.Model.Commands.SignInCommand;
import com.wineinventory.Authorization.Domain.Model.Commands.SignUpCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;

import java.util.Optional;

/**
 * User command service
 * This interface represents the service to handle user commands.
 */
public interface UserCommandService {

    /**
     * Handle sign in command
     * @param command the {@link SignInCommand} command
     * @return an {@link Optional} of {@link ImmutablePair} of {@link User} and {@link String}
     */
    Optional<ImmutableTriple<User, String, Long>> handle(SignInCommand command);

    /**
     * Handle sign-up command
     * @param command the {@link SignUpCommand} command
     * @return an {@link Optional} of {@link User} entity
     */
    Optional<User> handle(SignUpCommand command);
}