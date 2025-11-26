package com.wineinventory.planmanagement.interfaces.rest.assemblers;

import com.wineinventory.planmanagement.domain.model.aggregates.Plan;
import com.wineinventory.planmanagement.interfaces.rest.resources.PlanResource;

public class PlanResourceAssembler {

    public static PlanResource toResource(Plan plan) {
        return new PlanResource(
                plan.getId(),
                plan.getCode(),
                plan.getName(),
                plan.getDescription(),
                plan.getPrice(),
                plan.getCurrency(),
                plan.getPaypalPlanId()
        );
    }
}
