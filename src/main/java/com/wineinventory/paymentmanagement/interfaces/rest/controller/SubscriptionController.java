package com.wineinventory.paymentmanagement.interfaces.rest.controller;

import com.wineinventory.paymentmanagement.application.internal.CommandServices.SubscriptionCommandService;
import com.wineinventory.paymentmanagement.application.internal.QueryServices.SubscriptionQueryService;
import com.wineinventory.paymentmanagement.domain.model.aggregates.Subscription;
import com.wineinventory.paymentmanagement.domain.model.commands.CreateSubscriptionCommand;
import com.wineinventory.paymentmanagement.domain.model.commands.CancelSubscriptionCommand;
import com.wineinventory.paymentmanagement.external.paypal.PayPalSubscriptionService;
import com.wineinventory.paymentmanagement.external.paypal.dtos.PayPalCreateSubscriptionResponse;
import com.wineinventory.paymentmanagement.interfaces.rest.assemblers.SubscriptionResourceAssembler;
import com.wineinventory.paymentmanagement.interfaces.rest.resources.CreateSubscriptionResource;
import com.wineinventory.paymentmanagement.interfaces.rest.resources.SubscriptionCreatedResource;
import com.wineinventory.paymentmanagement.interfaces.rest.resources.SubscriptionResource;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionCommandService commandService;
    private final SubscriptionQueryService queryService;
    private final PayPalSubscriptionService payPalSubscriptionService;

    public SubscriptionController(
            SubscriptionCommandService commandService,
            SubscriptionQueryService queryService,
            PayPalSubscriptionService payPalSubscriptionService) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.payPalSubscriptionService = payPalSubscriptionService;
    }

    /** ----------------------------------------------------------
     *   CREAR SUSCRIPCIÓN — PASO 1: CREAR LOCAL & EN PAYPAL
     * ---------------------------------------------------------- */
    @PostMapping
    public SubscriptionCreatedResource create(@RequestBody CreateSubscriptionResource resource) {

        // 1. Crear suscripción interna + PayPal desde el CommandService
        Subscription subscription = commandService.handle(new CreateSubscriptionCommand(
                resource.userId(),
                resource.planId(),
                resource.paypalPlanId(),
                resource.currency(),
                resource.amount()
        ));

        // 2. Crear suscripción en PayPal para obtener el link de aprobación
        PayPalCreateSubscriptionResponse response =
                payPalSubscriptionService.createSubscription(resource.paypalPlanId());

        // 3. Extraer link de aprobación "approve"
        String approvalLink = response.links().stream()
                .filter(link -> link.rel().equals("approve"))
                .findFirst()
                .map(link -> link.href())
                .orElse(null);

        // 4. Devolver al frontend toda la info necesaria
        return new SubscriptionCreatedResource(
                subscription.getId(),
                subscription.getPaypalSubscriptionId(),
                subscription.getStatus(),
                approvalLink
        );
    }

    /** ----------------------------------------------------------
     *   CANCELAR SUSCRIPCIÓN
     * ---------------------------------------------------------- */
    @PostMapping("/{id}/cancel")
    public SubscriptionResource cancel(@PathVariable Long id) {
        var command = new CancelSubscriptionCommand(id);
        var subscription = commandService.handle(command);
        return SubscriptionResourceAssembler.toResource(subscription);
    }

    /** ----------------------------------------------------------
     *   OBTENER SUSCRIPCIÓN POR ID
     * ---------------------------------------------------------- */
    @GetMapping("/{id}")
    public SubscriptionResource getById(@PathVariable Long id) {
        var subscription = queryService.handle(id);
        return SubscriptionResourceAssembler.toResource(subscription);
    }

    /** ----------------------------------------------------------
     *   OBTENER SUSCRIPCIONES DE UN USUARIO
     * ---------------------------------------------------------- */
    @GetMapping("/user/{userId}")
    public List<SubscriptionResource> getByUser(@PathVariable Long userId) {
        var subscriptions = queryService.handleFindByUser(userId);
        return subscriptions.stream()
                .map(SubscriptionResourceAssembler::toResource)
                .toList();
    }
}
