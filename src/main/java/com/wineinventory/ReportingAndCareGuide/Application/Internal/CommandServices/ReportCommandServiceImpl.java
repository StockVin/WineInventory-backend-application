package com.wineinventory.ReportingAndCareGuide.Application.Internal.CommandServices;

import com.wineinventory.ReportingAndCareGuide.Domain.Model.Aggregates.Report;
import com.wineinventory.ReportingAndCareGuide.Domain.Model.Commands.CreateReportCommand;
import com.wineinventory.ReportingAndCareGuide.Domain.Model.Commands.DeleteReportCommand;
import com.wineinventory.ReportingAndCareGuide.Domain.Model.Exceptions.DuplicateReportException;
import com.wineinventory.ReportingAndCareGuide.Domain.Services.ReportCommandService;
import com.wineinventory.ReportingAndCareGuide.Infrastructure.Persistence.JPA.Repositories.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * ReportCommandService Implementation
 *
 * @summary
 * Implementation of the ReportCommandService interface.
 * It is responsible for handling report commands.
 *
 * @since 1.0
 */
@Service
@Transactional
public class ReportCommandServiceImpl implements ReportCommandService {

    private static final String REPORT_EXISTS_MSG = "A report with the same date and lost amount already exists";
    private static final String COMMAND_NULL_MSG = "CreateReportCommand cannot be null";

    private final ReportRepository reportRepository;

    public ReportCommandServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = Objects.requireNonNull(reportRepository, "ReportRepository cannot be null");
    }

    /**
     * Handles the creation of a new Report based on the provided command.
     *
     * @param command The command containing details for creating a new Report
     * @return An Optional containing the created Report if successful
     */
    @Override
    public Optional<Report> handle(CreateReportCommand command) {
        if (command == null) {
            throw new IllegalArgumentException(COMMAND_NULL_MSG);
        }
        if (reportRepository.existsByReportDateAndLostAmount(
                command.reportDate(),
                command.lostAmount())) {
            throw new DuplicateReportException(REPORT_EXISTS_MSG);
        }

        Report report = new Report(command);
        Report savedReport = reportRepository.save(report);

        return Optional.of(savedReport);
    }
    /**
     * Handles the deletion of a Report based on the provided command.
     *
     * @param command The command containing the ID of the Report to delete
     */
    @Override
    public void handle(DeleteReportCommand command) {
        if (command == null || command.id() == null) {
            throw new IllegalArgumentException("DeleteReportCommand and its ID cannot be null");
        }
        if (!reportRepository.existsById(command.id())) {
            throw new IllegalArgumentException("Report not found");
        }
        reportRepository.deleteById(command.id());
    }

}