package com.wineinventory.paymentandsubscriptions.interfaces.rest.controller;

import com.wineinventory.paymentandsubscriptions.domain.repositories.PlanRepository;
import com.wineinventory.paymentandsubscriptions.interfaces.rest.assemblers.CreateSubscriptionAssembler;
import com.wineinventory.paymentandsubscriptions.interfaces.rest.resources.UpgradeSubscriptionResource;
import com.wineinventory.paymentandsubscriptions.interfaces.rest.assemblers.SubscriptionAssembler;
import com.wineinventory.paymentandsubscriptions.application.internal.outboundservices.paymentproviders.services.PaypalService;
import com.wineinventory.paymentandsubscriptions.application.internal.outboundservices.paymentproviders.models.PaypalOrder;
import com.wineinventory.paymentandsubscriptions.application.internal.outboundservices.paymentproviders.models.PaypalOrderItem;
import com.wineinventory.paymentandsubscriptions.application.internal.commandservices.SubscriptionCommandService;
import com.wineinventory.paymentandsubscriptions.domain.model.aggregates.Subscription;
import com.wineinventory.paymentandsubscriptions.domain.model.commands.CreateSubscriptionCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts/{accountId}/subscriptions")
@Tag(name = "Accounts", description = "Account Management")
public class AccountSubscriptionsController {

    private final SubscriptionCommandService subscriptionCommandService;
    private final PaypalService paypalService;
    private final PlanRepository planRepository;

    public AccountSubscriptionsController(
            SubscriptionCommandService subscriptionCommandService,
            PaypalService paypalService,
            PlanRepository planRepository) {
        this.subscriptionCommandService = subscriptionCommandService;
        this.paypalService = paypalService;
        this.planRepository = planRepository;
    }

    @PostMapping
    @Operation(summary = "Create a new subscription for an account", 
               description = "Initializes a new subscription for the specified account.")
    public ResponseEntity<SubscriptionAssembler> createSubscription(
            @Parameter(description = "Account ID", required = true) @PathVariable String accountId,
            @RequestBody CreateSubscriptionAssembler request) {
        try {
            System.err.println("[createSubscription] accountId=" + accountId + ", selectedPlanId=" + request.selectedPlanId());
            var requested = request.selectedPlanId();
            var planOpt = planRepository.findByPlanId(requested);
            if (planOpt.isEmpty()) planOpt = planRepository.findByPlanId(requested != null ? requested.trim() : null);
            var plan = planOpt.orElseGet(() -> {
                var normalized = requested != null ? requested.trim().toLowerCase() : "";
                var all = planRepository.findAll();
                var match = all.stream()
                    .filter(p -> p.getPlanId() != null && p.getPlanId().trim().toLowerCase().equals(normalized))
                    .findFirst();
                if (match.isPresent()) return match.get();
                System.err.println("[createSubscription] plan not found. requested=" + requested + ", availablePlanIds=" +
                        all.stream().map(p -> p.getPlanId()).toList());
                throw new IllegalArgumentException("Plan not found");
            });
            System.err.println("[createSubscription] plan found: id=" + plan.getId() + ", planId=" + plan.getPlanId() + ", type=" + plan.getPlanType() + ", currency=" + plan.getCurrency() + ", price=" + plan.getPrice());

            var command = new CreateSubscriptionCommand(
                Long.parseLong(accountId), 
                Long.valueOf(plan.getId()), 
                "Free".equals(plan.getPlanType()) ? null : plan.getPaypalSubscriptionId(), 
                plan.getCurrency(),
                plan.getPrice()
            );
            System.err.println("[createSubscription] command built: userId=" + command.userId() + ", planDbId=" + command.planId() + ", paypalSubId=" + command.paypalSubscriptionId());

            Subscription subscription = subscriptionCommandService.handle(command);
            System.err.println("[createSubscription] subscription saved: id=" + subscription.getId() + ", status=" + subscription.getStatus());

            SubscriptionAssembler response = SubscriptionAssembler.fromSubscriptionWithPayment(
                subscription.getId().toString(),
                plan.getPlanId(), // use the plan's identifier
                subscription.getStatus(),
                subscription.getNextBillingDate() != null ? subscription.getNextBillingDate().toString() : "2024-12-31",
                "MONTHLY",
                "MONTHLY",
                plan.getMaxProducts(),
                null,
                null,
                "Subscription created successfully with plan's PayPal subscription ID."
            );

            return ResponseEntity.status(201).body(response);
            
        } catch (IllegalArgumentException e) {
            System.err.println("createSubscription error: " + e.getMessage());
            if ("Plan not found".equals(e.getMessage())) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            System.err.println("createSubscription unexpected error: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{subscriptionId}")
    @Operation(summary = "Upgrade an existing subscription for an account", 
               description = "Upgrades an existing subscription for the specified account.")
    public ResponseEntity<SubscriptionAssembler> upgradeSubscription(
            @Parameter(description = "Account ID", required = true) @PathVariable String accountId,
            @Parameter(description = "Subscription ID", required = true) @PathVariable String subscriptionId,
            @RequestBody UpgradeSubscriptionResource request) {
        try {
            List<PaypalOrderItem> items = List.of(
                new PaypalOrderItem(
                    "Subscription Upgrade", 
                    "Upgrade to " + request.newPlanId(),
                    "PLAN-" + request.newPlanId(),
                    BigDecimal.valueOf(149.99), 
                    BigDecimal.ZERO,
                    1,
                    "DIGITAL_GOODS"
                )
            );
            
            PaypalOrder paypalOrder = paypalService.createOrder(
                BigDecimal.valueOf(149.99), 
                "USD", 
                items
            );
            
            SubscriptionAssembler response = SubscriptionAssembler.fromSubscriptionWithPayment(
                subscriptionId,
                request.newPlanId(),
                "PENDING_UPGRADE",
                "2024-12-31",
                "MONTHLY",
                "MONTHLY",
                1000, 
                paypalOrder.getPaypalOrderId(),
                "https://www.paypal.com/checkoutnow?token=" + paypalOrder.getPaypalOrderId(),
                "Subscription upgrade initiated. Please complete payment."
            );
            
            return ResponseEntity.status(201).body(response);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    @Operation(summary = "Get subscription by account id", 
               description = "Retrieves a subscription for the specified account.")
    public ResponseEntity<SubscriptionAssembler> getSubscriptionByAccountId(
            @Parameter(description = "Account ID", required = true) @PathVariable String accountId) {
        try {
            SubscriptionAssembler response = SubscriptionAssembler.fromSubscription(
                "SUB-" + UUID.randomUUID().toString().substring(0, 8),
                "PLAN-001",
                "ACTIVE",
                "2024-12-31",
                "MONTHLY",
                "MONTHLY",
                500
            );
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
