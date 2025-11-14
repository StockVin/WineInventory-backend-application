package com.wineinventory.reportingandcareguide.application.acl;

import com.wineinventory.reportingandcareguide.domain.model.commands.DeleteReportCommand;
import com.wineinventory.reportingandcareguide.domain.model.queries.GetReportByIdQuery;
import com.wineinventory.reportingandcareguide.domain.model.queries.GetReportByProductNameQuery;
import com.wineinventory.reportingandcareguide.domain.model.queries.GetReportByReportDateAndLostAmountQuery;
import com.wineinventory.reportingandcareguide.domain.model.queries.GetReportByTypeQuery;
import com.wineinventory.reportingandcareguide.domain.services.ReportCommandService;
import com.wineinventory.reportingandcareguide.domain.services.ReportQueryService;
import com.wineinventory.reportingandcareguide.interfaces.rest.assemblers.CreateReportCommandFromResourceAssembler;
import com.wineinventory.reportingandcareguide.interfaces.rest.assemblers.ReportResourceFromEntityAssembler;
import com.wineinventory.reportingandcareguide.interfaces.rest.resources.CreateReportResource;
import com.wineinventory.reportingandcareguide.interfaces.rest.resources.ReportResource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ReportingContextFacade {

    private final ReportCommandService reportCommandService;
    private final ReportQueryService reportQueryService;

    public ReportingContextFacade(ReportCommandService reportCommandService, ReportQueryService reportQueryService) {
        this.reportCommandService = reportCommandService;
        this.reportQueryService = reportQueryService;
    }

    public Optional<ReportResource> createReport(CreateReportResource resource) {
        var command = CreateReportCommandFromResourceAssembler.toCommandFromResource(resource);
        return reportCommandService.handle(command)
                .map(ReportResourceFromEntityAssembler::toResourceFromEntity);
    }

    public boolean deleteReport(Long id) {
        try {
            reportCommandService.handle(new DeleteReportCommand(id));
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    public List<ReportResource> getAllReports() {
        return reportQueryService.getAllReports().stream()
                .map(ReportResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
    }

    public Optional<ReportResource> getReportById(Long id) {
        return reportQueryService.handle(new GetReportByIdQuery(id))
                .map(ReportResourceFromEntityAssembler::toResourceFromEntity);
    }

    public List<ReportResource> getReportsByProductId(String productId) {
        return reportQueryService.handle(new GetReportByProductNameQuery(productId)).stream()
                .map(ReportResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
    }

    public Optional<ReportResource> getReportByType(String type) {
        return reportQueryService.handle(new GetReportByTypeQuery(type))
                .map(ReportResourceFromEntityAssembler::toResourceFromEntity);
    }

    public Optional<ReportResource> getReportByReportDateAndLostAmount(Date reportDate, double lostAmount) {
        return reportQueryService.handle(new GetReportByReportDateAndLostAmountQuery(reportDate, lostAmount))
                .map(ReportResourceFromEntityAssembler::toResourceFromEntity);
    }
}
