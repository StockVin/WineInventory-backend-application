package com.wineinventory.reportingandcareguide.domain.services;

import com.wineinventory.reportingandcareguide.domain.model.aggregates.Report;
import com.wineinventory.reportingandcareguide.domain.model.commands.CreateReportCommand;
import com.wineinventory.reportingandcareguide.domain.model.commands.DeleteReportCommand;

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
