package com.wineinventory.reportingandcareguide.interfaces.rest.assemblers;

import com.wineinventory.reportingandcareguide.domain.model.commands.CreateReportCommand;
import com.wineinventory.reportingandcareguide.interfaces.rest.resources.CreateReportResource;

/**
 * @summary
 * This class is used to convert CreateReportResource to CreateReportCommand.
 */
public class CreateReportCommandFromResourceAssembler {
    /**
     * Converts a CreateReportResource to a CreateReportCommand.
     *
     * @param resource The CreateReportResource to convert.
     * @return The CreateReportCommand.
     */
    public static CreateReportCommand toCommandFromResource(CreateReportResource resource) {
        return new CreateReportCommand(
                resource.productName(),
                resource.type(),
                resource.price(),
                resource.amount(),
                resource.reportDate(),
                resource.lostAmount(),
                resource.productNameText());
    }
}