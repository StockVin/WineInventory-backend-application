package com.wineinventory.profilemanagement.interfaces.rest.resources;

import com.wineinventory.authorization.domain.model.aggregates.User;
import com.wineinventory.authorization.domain.model.valueobjects.UserRoles;

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