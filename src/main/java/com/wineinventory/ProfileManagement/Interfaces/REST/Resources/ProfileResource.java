package com.wineinventory.ProfileManagement.Interfaces.REST.Resources;

import com.wineinventory.Authorization.Domain.Model.Aggregates.User;
import com.wineinventory.Authorization.Domain.Model.ValueObjects.UserRoles;

public record ProfileResource(
        String username,
        String email,
        String password,
        String validationPassword,
        UserRoles role
) {

    public static ProfileResource from(User user) {
        return new ProfileResource(
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getValidationPassword(),
                user.getRole()
        );
    }
}