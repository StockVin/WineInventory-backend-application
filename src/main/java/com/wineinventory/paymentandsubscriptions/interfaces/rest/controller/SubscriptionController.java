package com.wineinventory.paymentandsubscriptions.interfaces.rest.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wineinventory.paymentandsubscriptions.application.internal.commandservices.SubscriptionCommandService;
import com.wineinventory.paymentandsubscriptions.domain.model.commands.WebhookPaymentCommand;
import com.wineinventory.paymentandsubscriptions.interfaces.rest.assemblers.WebhookPaymentCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api/v1/subscriptions")
@Tag(name = "Subscriptions")
public class SubscriptionController {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionController.class);
    
    private final SubscriptionCommandService subscriptionsCommandService;
    private final ObjectMapper objectMapper;
    private final ExecutorService executorService;

    public SubscriptionController(
            SubscriptionCommandService subscriptionsCommandService,
            ObjectMapper objectMapper) {
        this.subscriptionsCommandService = subscriptionsCommandService;
        this.objectMapper = objectMapper;
        this.executorService = Executors.newCachedThreadPool();
    }

    @PostMapping
    @Operation(
        summary = "Creates a new subscription",
        description = "Endpoint to creates a subscription based on the provided notification from PayPal.",
        operationId = "ConfirmedSubscription"
    )
    public ResponseEntity<?> confirmedSubscription(@RequestBody String body) {
        try {
            logger.info("Received Webhook: {}", body);
            
            CompletableFuture.runAsync(() -> {
                try {
                    JsonNode rootNode = objectMapper.readTree(body);
                    
                    String dataId = null;
                    
                    if (rootNode.has("data") && rootNode.get("data").has("id")) {
                        dataId = rootNode.get("data").get("id").asText();
                    }
                    else if (rootNode.has("resource")) {
                        String resource = rootNode.get("resource").asText();
                        if (resource.contains("/")) {
                            String[] parts = resource.split("/");
                            dataId = parts[parts.length - 1];
                        } else {
                            dataId = resource;
                        }
                    }

                    if (dataId == null || dataId.trim().isEmpty()) {
                        logger.warn("Could not get payment ID from webhook");
                        return;
                    }

                    logger.info("Processing payment with ID: {}", dataId);

                    WebhookPaymentCommand webHookCommand = WebhookPaymentCommandFromResourceAssembler.toCommandFromResource(dataId);
                    
                    subscriptionsCommandService.handle(webHookCommand);

                    logger.info("Subscription updated successfully.");
                } catch (Exception ex) {
                    logger.error("Error processing webhook", ex);
                }
            }, executorService);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new WebhookResponse("Webhook received successfully"));
            
        } catch (Exception ex) {
            logger.error("Error processing webhook", ex);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new WebhookResponse("Webhook received with errors (but confirmed)"));
        }
    }

    public record WebhookResponse(String message) {}
}
