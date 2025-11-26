package com.wineinventory.paymentandsubscriptions.interfaces.rest.controller;

import com.wineinventory.paymentandsubscriptions.application.internal.queryservices.PlanQueryService;
import com.wineinventory.paymentandsubscriptions.interfaces.rest.assemblers.PlanResourceAssembler;
import com.wineinventory.paymentandsubscriptions.interfaces.rest.resources.PlanResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plans")
@Tag(name = "Plans", description = "Available endpoints for plans.")
public class PlanController {

    private final PlanQueryService queryService;

    public PlanController(PlanQueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping
    @Operation(summary = "Get all plans", description = "Retrieves a list of all available plans.")
    public List<PlanResource> getAll() {
        return queryService.handle()
                .stream()
                .map(PlanResourceAssembler::toResource)
                .toList();
    }
}
