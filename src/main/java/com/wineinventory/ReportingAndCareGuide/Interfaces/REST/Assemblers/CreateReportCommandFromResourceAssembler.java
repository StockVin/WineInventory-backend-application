package com.wineinventory.ReportingAndCareGuide.Interfaces.REST.Assemblers;

import com.wineinventory.ReportingAndCareGuide.Domain.Model.Commands.CreateReportCommand;
import com.wineinventory.ReportingAndCareGuide.Interfaces.REST.Resources.CreateReportResource;

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
        return new CreateReportCommand(resource.productName(),resource.type(),resource.price(),resource.amount(), resource.reportDate(), resource.lostAmount());
    }
}