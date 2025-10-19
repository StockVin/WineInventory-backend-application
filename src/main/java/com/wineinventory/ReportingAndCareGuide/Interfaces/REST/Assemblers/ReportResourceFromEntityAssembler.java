package com.wineinventory.ReportingAndCareGuide.Interfaces.REST.Assemblers;

import com.wineinventory.ReportingAndCareGuide.Domain.Model.Aggregates.Report;
import com.wineinventory.ReportingAndCareGuide.Interfaces.REST.Resources.ReportResource;

/**
 * @summary
 * This class is used to convert Report entities to Report resources.
 */
public class ReportResourceFromEntityAssembler {
    /**
     * Converts a Report entity to a Report resource.
     *
     * @param entity The Report entity to convert.
     * @return The Report resource.
     */
    public static ReportResource toResourceFromEntity(Report entity) {
        return new ReportResource(
                entity.getId(),
                entity.getProductId().productId().toString(),
                entity.getType(),
                entity.getPrice(),
                entity.getAmount(),
                entity.getReportDate(),
                entity.getLostAmount(),
                entity.getProductNameText()
        );
    }
}