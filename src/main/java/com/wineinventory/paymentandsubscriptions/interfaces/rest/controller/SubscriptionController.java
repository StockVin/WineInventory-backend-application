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
import org.springframework.beans.factory.annotation.Value;
import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/subscriptions")
@Tag(name = "Subscriptions")
public class SubscriptionController {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionController.class);
    
    private final SubscriptionCommandService subscriptionsCommandService;
    private final ObjectMapper objectMapper;
    private final ExecutorService executorService;
    @Value("${base-url}")
    private String baseUrl;

    public SubscriptionController(
            SubscriptionCommandService subscriptionsCommandService,
            ObjectMapper objectMapper) {
        this.subscriptionsCommandService = subscriptionsCommandService;
        this.objectMapper = objectMapper;
        this.executorService = Executors.newCachedThreadPool();
    }

    @GetMapping("/paypal/return")
    @Operation(
        summary = "PayPal return URL",
        description = "Handles PayPal approval return and redirects to frontend.")
    public ResponseEntity<?> paypalReturn(@RequestParam Map<String, String> params) {
        String redirect = params.getOrDefault("redirectTo", baseUrl != null ? baseUrl : "/");
        String subscriptionId = params.getOrDefault("subscription_id", "");
        String token = params.getOrDefault("token", "");
        String baToken = params.getOrDefault("ba_token", "");
        if (subscriptionId != null && !subscriptionId.isBlank()) {
            CompletableFuture.runAsync(() -> {
                try {
                    WebhookPaymentCommand cmd = WebhookPaymentCommandFromResourceAssembler.toCommandFromResource(subscriptionId);
                    subscriptionsCommandService.handle(cmd);
                } catch (Exception e) {
                    logger.warn("Failed to trigger activation on return for subscription {}", subscriptionId, e);
                }
            }, executorService);
        }

        boolean inline = "page".equalsIgnoreCase(params.getOrDefault("view", ""))
                || "false".equalsIgnoreCase(params.getOrDefault("redirect", ""));

        if (inline) {
            String html = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n<head>\n<meta charset=\"UTF-8\"/>\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"/>\n" +
                    "<title>Subscription Approved</title>\n" +
                    "<style>body{font-family:system-ui,-apple-system,Segoe UI,Roboto,Ubuntu,\n \n 'Helvetica Neue',Arial,'Noto Sans',sans-serif;display:flex;align-items:center;justify-content:center;min-height:100vh;background:#f7f7f8;margin:0} .card{background:#fff;border:1px solid #e5e7eb;border-radius:12px;padding:24px;max-width:640px;box-shadow:0 2px 10px rgba(0,0,0,0.06)} h1{margin:0 0 8px;font-size:22px} p{margin:4px 0;color:#374151} code{background:#f3f4f6;padding:2px 6px;border-radius:6px} .ok{color:#065f46;font-weight:600}</style>\n" +
                    "</head><body>\n<div class=\"card\">\n" +
                    "<h1 class=\"ok\">Payment approved</h1>\n" +
                    "<p>Subscription ID: <code>" + subscriptionId + "</code></p>\n" +
                    "<p>Token: <code>" + token + "</code></p>\n" +
                    "<p>Billing Agreement Token: <code>" + baToken + "</code></p>\n" +
                    "</div>\n</body></html>";
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(html);
        }

        String location = String.format("%s?paymentStatus=approved&subscription_id=%s&token=%s&ba_token=%s",
                redirect, subscriptionId, token, baToken);
        return ResponseEntity.status(302).location(URI.create(location)).build();
    }

    @GetMapping("/paypal/cancel")
    @Operation(
        summary = "PayPal cancel URL",
        description = "Handles PayPal cancel return and redirects to frontend.")
    public ResponseEntity<?> paypalCancel(@RequestParam Map<String, String> params) {
        String redirect = baseUrl != null ? baseUrl : "/";
        String token = params.getOrDefault("token", "");

        boolean inline = "page".equalsIgnoreCase(params.getOrDefault("view", ""))
                || "false".equalsIgnoreCase(params.getOrDefault("redirect", ""));

        if (inline) {
            String html = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n<head>\n<meta charset=\"UTF-8\"/>\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"/>\n" +
                    "<title>Subscription Cancelled</title>\n" +
                    "<style>body{font-family:system-ui,-apple-system,Segoe UI,Roboto,Ubuntu,\n \n 'Helvetica Neue',Arial,'Noto Sans',sans-serif;display:flex;align-items:center;justify-content:center;min-height:100vh;background:#f7f7f8;margin:0} .card{background:#fff;border:1px solid #e5e7eb;border-radius:12px;padding:24px;max-width:640px;box-shadow:0 2px 10px rgba(0,0,0,0.06)} h1{margin:0 0 8px;font-size:22px} p{margin:4px 0;color:#374151} code{background:#f3f4f6;padding:2px 6px;border-radius:6px} .warn{color:#7c2d12;font-weight:600}</style>\n" +
                    "</head><body>\n<div class=\"card\">\n" +
                    "<h1 class=\"warn\">Payment cancelled</h1>\n" +
                    "<p>Token: <code>" + token + "</code></p>\n" +
                    "</div>\n</body></html>";
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(html);
        }

        String location = String.format("%s?paymentStatus=cancelled&token=%s", redirect, token);
        return ResponseEntity.status(302).location(URI.create(location)).build();
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
                    String eventType = rootNode.has("event_type") ? rootNode.get("event_type").asText() : "";
                    String dataId = null;

                    if (rootNode.has("resource")) {
                        JsonNode resourceNode = rootNode.get("resource");
                        if (resourceNode.has("id")) {
                            dataId = resourceNode.get("id").asText();
                        }
                    }
                    if ((dataId == null || dataId.isBlank()) && rootNode.has("data") && rootNode.get("data").has("id")) {
                        dataId = rootNode.get("data").get("id").asText();
                    }

                    if (dataId == null || dataId.trim().isEmpty()) {
                        logger.warn("Could not get payment ID from webhook. Body: {}", body);
                        return;
                    }

                    logger.info("Processing webhook event: {} with resource.id: {}", eventType, dataId);

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
