package com.wineinventory.reportingandcareguide.interfaces.rest.assemblers;

import com.wineinventory.reportingandcareguide.domain.model.commands.CreateCareGuideCommand;
import com.wineinventory.reportingandcareguide.interfaces.rest.resources.CreateCareGuideResource;

/**
 * @summary
 * This class is used to convert CreateCareGuideResource to CreateCareGuideCommand.
 */
public class CreateCareGuideCommandFromResourceAssembler {

    /**
     * Converts a CreateCareGuideResource to a CreateCareGuideCommand.
     *
     * @param resource The CreateCareGuideResource to convert.
     * @return The CreateCareGuideCommand.
     */
    public static CreateCareGuideCommand toCommandFromResource(CreateCareGuideResource resource) {
        return new CreateCareGuideCommand(
                resource.guideName(),
                resource.type(),
                resource.description(),
                resource.image() != null ? resource.image().getOriginalFilename() : "",
                "",
                null
        );
    }
}