package com.wineinventory.ReportingAndCareGuide.Application.Internal.QueryServices;

import com.wineinventory.ReportingAndCareGuide.Domain.Model.Aggregates.Report;
import com.wineinventory.ReportingAndCareGuide.Domain.Model.Queries.GetReportByIdQuery;
import com.wineinventory.ReportingAndCareGuide.Domain.Model.Queries.GetReportByProductNameQuery;
import com.wineinventory.ReportingAndCareGuide.Domain.Model.Queries.GetReportByReportDateAndLostAmountQuery;
import com.wineinventory.ReportingAndCareGuide.Domain.Model.Queries.GetReportByTypeQuery;
import com.wineinventory.ReportingAndCareGuide.Domain.Services.ReportQueryService;
import com.wineinventory.ReportingAndCareGuide.Infrastructure.Persistence.JPA.Repositories.ReportRepository;
import com.wineinventory.Authorization.Infrastructure.Persistence.JPA.Repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

/**
 * ReportQueryService Implementation
 *
 * @summary
 * Implementation of the ReportQueryService interface.
 * It is responsible for handling report queries.
 *
 * @since 1.0
 */
@Service
public class ReportQueryServiceImpl implements ReportQueryService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public ReportQueryServiceImpl(ReportRepository reportRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all reports from the repository.
     *
     * @return A list of all Report entities
     */
    @Override
    public List<Report> getAllReports() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userRepository.findByUsername(username)
                .map(u -> u.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Authenticated user not found"));
        return reportRepository.findAllByUserId(userId);
    }

    /**
     * Retrieves a report by its unique identifier.
     *
     * @param id The ID of the report to retrieve
     * @return An Optional containing the found Report or empty if not found
     */
    @Override
    public Optional<Report> getReportById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userRepository.findByUsername(username)
                .map(u -> u.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Authenticated user not found"));
        return reportRepository.findByIdAndUserId(id, userId);
    }
    /**
     * Handles the retrieval of reports by product name.
     *
     * @param query The query containing the product name to search for
     * @return A list of Report entities matching the product name
     */
    @Override
    public List<Report> handle(GetReportByProductNameQuery query) {
        if (query == null) {
            throw new IllegalArgumentException("Query must not be null");
        }
        return reportRepository.findByProductId_ProductId(query.getProductIdAsLong());
    }
    /**
     * Handles the retrieval of a report by its ID.
     *
     * @param query The query containing the report ID to search for
     * @return An Optional containing the found Report or empty if not found
     */
    @Override
    public Optional<Report> handle(GetReportByIdQuery query) {
        if (query == null || query.id() == null) {
            throw new IllegalArgumentException("Query and report ID must not be null");
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userRepository.findByUsername(username)
                .map(u -> u.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Authenticated user not found"));
        return reportRepository.findByIdAndUserId(query.id(), userId);
    }
    /**
     * Handles the retrieval of a report by its report date and lost amount.
     *
     * @param query The query containing the report date and lost amount to search for
     * @return An Optional containing the first matching Report or empty if none found
     */
    @Override
    public Optional<Report> handle(GetReportByReportDateAndLostAmountQuery query) {
        if (query == null) {
            throw new IllegalArgumentException("Query cannot be null");
        }
        List<Report> reports = reportRepository.findByReportDateAndLostAmount(
                query.reportDate(),
                query.lostAmount()
        );
        return reports.isEmpty() ? Optional.empty() : Optional.of(reports.get(0));
    }
    /**
     * Handles the retrieval of a report by its type.
     *
     * @param query The query containing the report type to search for
     * @return An Optional containing the first matching Report or empty if none found
     */
    @Override
    public Optional<Report> handle(GetReportByTypeQuery query) {
        if (query == null || query.type() == null || query.type().isBlank()) {
            throw new IllegalArgumentException("Query and report type must not be null or empty");
        }
        return reportRepository.findByType(query.type()).stream().findFirst();
    }
}
