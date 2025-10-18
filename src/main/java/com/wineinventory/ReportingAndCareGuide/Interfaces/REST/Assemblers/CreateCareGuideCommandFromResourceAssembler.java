package com.wineinventory.ReportingAndCareGuide.Interfaces.REST.Assemblers;

import com.wineinventory.ReportingAndCareGuide.Domain.Model.Commands.CreateCareGuideCommand;
import com.wineinventory.ReportingAndCareGuide.Interfaces.REST.Resources.CreateCareGuideResource;

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