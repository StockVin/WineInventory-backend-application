package com.wineinventory.ReportingAndCareGuide.Domain.Services;

import com.wineinventory.ReportingAndCareGuide.Domain.Model.Entities.CareGuide;
import com.wineinventory.ReportingAndCareGuide.Domain.Model.Queries.GetAllCareGuidesByAccountIdQuery;
import com.wineinventory.ReportingAndCareGuide.Domain.Model.Queries.GetCareGuideByIdQuery;
import com.wineinventory.ReportingAndCareGuide.Domain.Model.Queries.GetCareGuideByTypeAndDescriptionQuery;

import java.util.List;

/**
 * @name CareGuideQueryService
 *
 * @summary
 * This interface represents the service to handle care guide queries.
 * @since 1.0.0
 */
public interface CareGuideQueryService {
    List<CareGuide> handle(GetCareGuideByIdQuery query);
    List<CareGuide> handle (GetCareGuideByTypeAndDescriptionQuery query);
    List<CareGuide> handle(GetAllCareGuidesByAccountIdQuery query);
}