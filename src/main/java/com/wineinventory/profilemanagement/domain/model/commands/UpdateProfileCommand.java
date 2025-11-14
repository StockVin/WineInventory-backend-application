package com.wineinventory.profilemanagement.domain.model.commands;

import com.wineinventory.authorization.domain.model.valueobjects.UserRoles;

public record UpdateProfileCommand(
        String currentUsername,
        String username,
        String email,
        String password,
        String validationPassword,
        UserRoles role
) {
}
