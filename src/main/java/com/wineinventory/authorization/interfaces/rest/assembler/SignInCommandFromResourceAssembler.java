package com.wineinventory.authorization.interfaces.rest.assembler;

import com.wineinventory.authorization.domain.model.commands.SignInCommand;
import com.wineinventory.authorization.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource signInResource) {
        return new SignInCommand(signInResource.username(), signInResource.password());
    }
}