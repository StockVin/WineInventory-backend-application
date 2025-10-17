package com.wineinventory.ReportingAndCareGuide.Domain.Services;

import com.wineinventory.ReportingAndCareGuide.Domain.Model.Aggregates.Report;
import com.wineinventory.ReportingAndCareGuide.Domain.Model.Commands.CreateReportCommand;
import com.wineinventory.ReportingAndCareGuide.Domain.Model.Commands.DeleteReportCommand;

import java.util.Optional;

/**
 * @name ReportCommandService
 * @summary
 * This interface represents the service to handle report commands.
 */
public interface ReportCommandService {
        /**
     * Handles the create report command.
     * @param command The create report command.
     * @return The created report.
     *
     * @throws IllegalArgumentException If productId, type, price, amount, reportDate or lostAmount is null or empty
     * @see CreateReportCommand
     */
    Optional<Report> handle(CreateReportCommand command);

    /**
     * Handles the delete report command.
     * @param command The delete report command.
     *
     * @throws IllegalArgumentException If id is null or less than or equal to 0
     * @see DeleteReportCommand
     */
    void handle(DeleteReportCommand command);
}
