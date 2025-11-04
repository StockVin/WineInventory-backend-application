package com.wineinventory.ProfileManagement.Interfaces.REST.Assembler;

import com.wineinventory.ProfileManagement.Domain.Model.Commands.UpdateProfileCommand;
import com.wineinventory.ProfileManagement.Interfaces.REST.Resources.ProfileResource;

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
