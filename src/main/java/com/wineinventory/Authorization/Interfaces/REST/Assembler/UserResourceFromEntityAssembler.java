package com.wineinventory.Authorization.Interfaces.REST.Assembler;

import com.wineinventory.Authorization.Domain.Model.Aggregates.User;
import com.wineinventory.Authorization.Interfaces.REST.Resources.UserResource;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User user) {
        return new UserResource(user.getUserId(), user.getUsername());
    }
}