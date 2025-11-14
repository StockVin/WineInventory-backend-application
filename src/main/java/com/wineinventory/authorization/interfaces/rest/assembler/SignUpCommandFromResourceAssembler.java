package com.wineinventory.authorization.interfaces.rest.assembler;

import com.wineinventory.authorization.domain.model.commands.SignUpCommand;
import com.wineinventory.authorization.interfaces.rest.resources.SignUpResource;

public class SignUpCommandFromResourceAssembler {
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        return new SignUpCommand(resource.username(), resource.email(), resource.password(), resource.validationPassword(), resource.role());
    }
}