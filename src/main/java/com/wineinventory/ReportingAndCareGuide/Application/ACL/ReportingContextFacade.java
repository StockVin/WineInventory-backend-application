package com.wineinventory.ReportingAndCareGuide.Application.ACL;

import com.wineinventory.ReportingAndCareGuide.Domain.Model.Commands.DeleteReportCommand;
import com.wineinventory.ReportingAndCareGuide.Domain.Model.Queries.GetReportByIdQuery;
import com.wineinventory.ReportingAndCareGuide.Domain.Model.Queries.GetReportByProductNameQuery;
import com.wineinventory.ReportingAndCareGuide.Domain.Model.Queries.GetReportByReportDateAndLostAmountQuery;
import com.wineinventory.ReportingAndCareGuide.Domain.Model.Queries.GetReportByTypeQuery;
import com.wineinventory.ReportingAndCareGuide.Domain.Services.ReportCommandService;
import com.wineinventory.ReportingAndCareGuide.Domain.Services.ReportQueryService;
import com.wineinventory.ReportingAndCareGuide.Interfaces.REST.Assemblers.CreateReportCommandFromResourceAssembler;
import com.wineinventory.ReportingAndCareGuide.Interfaces.REST.Assemblers.ReportResourceFromEntityAssembler;
import com.wineinventory.ReportingAndCareGuide.Interfaces.REST.Resources.CreateReportResource;
import com.wineinventory.ReportingAndCareGuide.Interfaces.REST.Resources.ReportResource;
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
