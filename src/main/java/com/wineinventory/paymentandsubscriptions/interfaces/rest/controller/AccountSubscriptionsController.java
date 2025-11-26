package com.wineinventory.paymentandsubscriptions.interfaces.rest.controller;

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

    public AccountSubscriptionsController(
            SubscriptionCommandService subscriptionCommandService,
            PaypalService paypalService) {
        this.subscriptionCommandService = subscriptionCommandService;
        this.paypalService = paypalService;
    }

    @PostMapping
    @Operation(summary = "Create a new subscription for an account", 
               description = "Initializes a new subscription for the specified account.")
    public ResponseEntity<SubscriptionAssembler> createSubscription(
            @Parameter(description = "Account ID", required = true) @PathVariable String accountId,
            @RequestBody CreateSubscriptionAssembler request) {
        try {
            var command = new CreateSubscriptionCommand(
                UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE, 
                Long.parseLong(request.selectedPlanId()), 
                "PLAN-" + request.selectedPlanId(), 
                "USD",
                99.99 
            );
            
            Subscription subscription = subscriptionCommandService.handle(command);
            
            List<PaypalOrderItem> items = List.of(
                new PaypalOrderItem(
                    "Subscription Plan", 
                    "Monthly subscription for " + request.selectedPlanId(),
                    "PLAN-" + request.selectedPlanId(),
                    BigDecimal.valueOf(99.99),
                    BigDecimal.ZERO,
                    1,
                    "DIGITAL_GOODS"
                )
            );
            
            PaypalOrder paypalOrder = paypalService.createOrder(
                BigDecimal.valueOf(99.99), 
                "USD", 
                items
            );
            
            SubscriptionAssembler response = SubscriptionAssembler.fromSubscriptionWithPayment(
                subscription.getId().toString(),
                subscription.getPlanId().toString(),
                subscription.getStatus(),
                subscription.getNextBillingDate() != null ? subscription.getNextBillingDate().toString() : "2024-12-31",
                "MONTHLY",
                "MONTHLY",
                500, 
                paypalOrder.getPaypalOrderId(),
                "https://www.paypal.com/checkoutnow?token=" + paypalOrder.getPaypalOrderId(),
                "Subscription created successfully. Please complete payment."
            );
            
            return ResponseEntity.status(201).body(response);
            
        } catch (Exception e) {
            System.err.println("Error creating subscription: " + e.getMessage());
            return ResponseEntity.badRequest().build();
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
            System.err.println("Error upgrading subscription: " + e.getMessage());
            return ResponseEntity.badRequest().build();
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
            System.err.println("Error retrieving subscription: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
