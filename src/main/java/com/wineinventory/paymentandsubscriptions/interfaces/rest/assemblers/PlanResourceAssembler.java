package com.wineinventory.paymentandsubscriptions.interfaces.rest.assemblers;

import com.wineinventory.paymentandsubscriptions.domain.model.entities.Plan;
import com.wineinventory.paymentandsubscriptions.interfaces.rest.resources.PlanResource;

public class PlanResourceAssembler {

    public static PlanResource toResource(Plan plan) {
        return new PlanResource(
                plan.getPlanId(),
                plan.getPaypalPlanId(),
                plan.getPaypalSubscriptionId(),
                plan.getPlanType(),
                plan.getDescription(),
                plan.getPaymentFrequency(),
                plan.getPrice(),
                plan.getCurrency(),
                plan.getMaxProducts()
        );
    }
}
