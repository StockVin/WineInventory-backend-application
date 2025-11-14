package com.wineinventory.reportingandcareguide.interfaces.rest.assemblers;

import com.wineinventory.reportingandcareguide.domain.model.aggregates.Report;
import com.wineinventory.reportingandcareguide.interfaces.rest.resources.ReportResource;

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