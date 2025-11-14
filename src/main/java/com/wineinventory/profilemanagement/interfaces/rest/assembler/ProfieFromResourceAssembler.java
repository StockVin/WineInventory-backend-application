package com.wineinventory.profilemanagement.interfaces.rest.assembler;

import com.wineinventory.profilemanagement.domain.model.commands.UpdateProfileCommand;
import com.wineinventory.profilemanagement.interfaces.rest.resources.ProfileResource;

public class ProfieFromResourceAssembler {

    private ProfieFromResourceAssembler() {
    }

    public static UpdateProfileCommand toCommand(String currentUsername, ProfileResource resource) {
        return new UpdateProfileCommand(
                currentUsername,
                resource.username(),
                resource.email(),
                resource.password(),
                resource.validationPassword(),
                resource.role()
        );
    }
}
