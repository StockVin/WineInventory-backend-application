package com.wineinventory.authorization.interfaces.rest.assembler;

import com.wineinventory.authorization.domain.model.aggregates.User;
import com.wineinventory.authorization.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {

    public static AuthenticatedUserResource toResourceFromEntity(User user, String token, Long accountId) {
        return new AuthenticatedUserResource(user.getUserId(), user.getUsername(), token, accountId);
    }
}