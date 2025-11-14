package com.wineinventory.reportingandcareguide.interfaces.rest.assemblers;

import com.wineinventory.reportingandcareguide.domain.model.entities.CareGuide;
import com.wineinventory.reportingandcareguide.interfaces.rest.resources.CareGuideResource;

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