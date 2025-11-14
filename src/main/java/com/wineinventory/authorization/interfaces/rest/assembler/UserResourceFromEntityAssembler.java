package com.wineinventory.authorization.interfaces.rest.assembler;

import com.wineinventory.authorization.domain.model.aggregates.User;
import com.wineinventory.authorization.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User user) {
        return new UserResource(user.getUserId(), user.getUsername());
    }
}