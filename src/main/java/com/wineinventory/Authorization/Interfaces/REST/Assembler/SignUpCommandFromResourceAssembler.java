package com.wineinventory.Authorization.Interfaces.REST.Assembler;

import com.wineinventory.Authorization.Domain.Model.Commands.SignUpCommand;
import com.wineinventory.Authorization.Interfaces.REST.Resources.SignUpResource;

public class SignUpCommandFromResourceAssembler {
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        return new SignUpCommand(resource.username(), resource.email(), resource.password(), resource.validationPassword(), resource.role());
    }
}