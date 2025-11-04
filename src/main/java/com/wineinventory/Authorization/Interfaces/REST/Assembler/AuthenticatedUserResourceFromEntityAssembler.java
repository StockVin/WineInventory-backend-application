package com.wineinventory.Authorization.Interfaces.REST.Assembler;

import com.wineinventory.Authorization.Domain.Model.Aggregates.User;
import com.wineinventory.Authorization.Interfaces.REST.Resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {

    public static AuthenticatedUserResource toResourceFromEntity(User user, String token, Long accountId) {
        return new AuthenticatedUserResource(user.getUserId(), user.getUsername(), token, accountId);
    }
}