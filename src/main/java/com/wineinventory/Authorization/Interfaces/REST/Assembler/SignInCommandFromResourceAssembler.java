package com.wineinventory.Authorization.Interfaces.REST.Assembler;

import com.wineinventory.Authorization.Domain.Model.Commands.SignInCommand;
import com.wineinventory.Authorization.Interfaces.REST.Resources.SignInResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource signInResource) {
        return new SignInCommand(signInResource.username(), signInResource.password());
    }
}