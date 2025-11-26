package com.wineinventory.planmanagement.interfaces.rest.controller;

import com.wineinventory.planmanagement.application.internal.CommandServices.PlanCommandService;
import com.wineinventory.planmanagement.application.internal.QueryServices.PlanQueryService;
import com.wineinventory.planmanagement.domain.model.commands.CreatePlanCommand;
import com.wineinventory.planmanagement.interfaces.rest.assemblers.PlanResourceAssembler;
import com.wineinventory.planmanagement.interfaces.rest.resources.CreatePlanResource;
import com.wineinventory.planmanagement.interfaces.rest.resources.PlanResource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
public class PlanController {

    private final PlanCommandService commandService;
    private final PlanQueryService queryService;

    public PlanController(PlanCommandService commandService,
                          PlanQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    public PlanResource create(@RequestBody CreatePlanResource resource) {
        var command = new CreatePlanCommand(
                resource.code(),
                resource.name(),
                resource.description(),
                resource.price(),
                resource.currency(),
                resource.paypalPlanId()
        );
        var plan = commandService.handle(command);
        return PlanResourceAssembler.toResource(plan);
    }

    @GetMapping
    public List<PlanResource> getAll() {
        return queryService.handle()
                .stream()
                .map(PlanResourceAssembler::toResource)
                .toList();
    }

    @GetMapping("/{id}")
    public PlanResource getById(@PathVariable Long id) {
        var plan = queryService.handle(id);
        return PlanResourceAssembler.toResource(plan);
    }
}
