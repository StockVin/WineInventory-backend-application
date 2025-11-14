package com.wineinventory.reportingandcareguide.domain.services;

import com.wineinventory.reportingandcareguide.domain.model.entities.CareGuide;
import com.wineinventory.reportingandcareguide.domain.model.queries.GetAllCareGuidesByAccountIdQuery;
import com.wineinventory.reportingandcareguide.domain.model.queries.GetCareGuideByIdQuery;
import com.wineinventory.reportingandcareguide.domain.model.queries.GetCareGuideByTypeAndDescriptionQuery;

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
    List<CareGuide> getAllCareGuides();
}