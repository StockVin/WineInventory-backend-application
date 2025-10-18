package com.wineinventory.ProfileManagement.Domain.Model.Commands;

import com.wineinventory.Authorization.Domain.Model.ValueObjects.UserRoles;

public record UpdateProfileCommand(
        String currentUsername,
        String username,
        String email,
        String password,
        String validationPassword,
        UserRoles role
) {
}
