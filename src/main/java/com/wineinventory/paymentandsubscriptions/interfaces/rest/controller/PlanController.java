package com.wineinventory.paymentandsubscriptions.interfaces.rest.controller;

import com.wineinventory.paymentandsubscriptions.application.internal.queryservices.PlanQueryService;
import com.wineinventory.paymentandsubscriptions.interfaces.rest.assemblers.PlanResourceAssembler;
import com.wineinventory.paymentandsubscriptions.interfaces.rest.resources.PlanResource;
import com.wineinventory.paymentandsubscriptions.interfaces.rest.resources.UpdatePlanPaypalIdResource;
import com.wineinventory.paymentandsubscriptions.domain.repositories.PlanRepository;
import com.wineinventory.paymentandsubscriptions.domain.model.entities.Plan;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plans")
@Tag(name = "Plans", description = "Available endpoints for plans.")
public class PlanController {

    private final PlanQueryService queryService;
    private final PlanRepository planRepository;

    public PlanController(PlanQueryService queryService, PlanRepository planRepository) {
        this.queryService = queryService;
        this.planRepository = planRepository;
    }

    @GetMapping
    @Operation(summary = "Get all plans", description = "Retrieves a list of all available plans.")
    public List<PlanResource> getAll() {
        return queryService.handle()
                .stream()
                .map(PlanResourceAssembler::toResource)
                .toList();
    }

    @PatchMapping("/{planId}/paypal-plan")
    @Operation(summary = "Update PayPal Plan ID", description = "Updates the paypalPlanId for the specified plan.")
    public PlanResource updatePaypalPlanId(
            @PathVariable String planId,
            @RequestBody UpdatePlanPaypalIdResource request
    ) {
        var plan = planRepository.findByPlanId(planId)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found"));

        plan.update(
                plan.getPlanId(),
                request.paypalPlanId(),
                plan.getPaypalSubscriptionId(),
                plan.getPlanType(),
                plan.getDescription(),
                plan.getPaymentFrequency(),
                plan.getPrice(),
                plan.getCurrency(),
                plan.getMaxProducts()
        );

        Plan saved = planRepository.save(plan);
        return PlanResourceAssembler.toResource(saved);
    }
}
