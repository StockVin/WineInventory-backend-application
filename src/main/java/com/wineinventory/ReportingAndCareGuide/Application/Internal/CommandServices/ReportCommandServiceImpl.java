package com.wineinventory.ReportingAndCareGuide.Application.Internal.CommandServices;

import com.wineinventory.ReportingAndCareGuide.Domain.Model.Aggregates.Report;
import com.wineinventory.ReportingAndCareGuide.Domain.Model.Commands.CreateReportCommand;
import com.wineinventory.ReportingAndCareGuide.Domain.Model.Commands.DeleteReportCommand;
import com.wineinventory.ReportingAndCareGuide.Domain.Model.Exceptions.DuplicateReportException;
import com.wineinventory.ReportingAndCareGuide.Domain.Services.ReportCommandService;
import com.wineinventory.ReportingAndCareGuide.Infrastructure.Persistence.JPA.Repositories.ReportRepository;
import com.wineinventory.Authorization.Infrastructure.Persistence.JPA.Repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

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
    private final UserRepository userRepository;

    public ReportCommandServiceImpl(ReportRepository reportRepository, UserRepository userRepository) {
        this.reportRepository = Objects.requireNonNull(reportRepository, "ReportRepository cannot be null");
        this.userRepository = Objects.requireNonNull(userRepository, "UserRepository cannot be null");
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

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userRepository.findByUsername(username)
                .map(u -> u.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Authenticated user not found"));

        Report report = new Report(command, userId);
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
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userRepository.findByUsername(username)
                .map(u -> u.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Authenticated user not found"));

        if (!reportRepository.existsByIdAndUserId(command.id(), userId)) {
            throw new IllegalArgumentException("Report not found");
        }
        reportRepository.deleteById(command.id());
    }

}