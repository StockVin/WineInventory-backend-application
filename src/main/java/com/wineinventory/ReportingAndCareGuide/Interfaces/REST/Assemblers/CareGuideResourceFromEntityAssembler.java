package com.wineinventory.ReportingAndCareGuide.Interfaces.REST.Assemblers;

import com.wineinventory.ReportingAndCareGuide.Domain.Model.Entities.CareGuide;
import com.wineinventory.ReportingAndCareGuide.Interfaces.REST.Resources.CareGuideResource;

public class CareGuideResourceFromEntityAssembler {
    public static CareGuideResource toResourceFromEntity(CareGuide entity) {
        return new CareGuideResource(
                entity.getId(),
                entity.getGuideName(),
                entity.getType(),
                entity.getDescription(),
                entity.getImageUrl() != null ? entity.getImageUrl().imageUrl() : null
        );
    }
}