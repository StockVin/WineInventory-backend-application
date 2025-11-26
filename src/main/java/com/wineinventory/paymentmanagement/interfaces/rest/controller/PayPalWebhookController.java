package com.wineinventory.paymentmanagement.interfaces.rest.controller;

import com.wineinventory.paymentmanagement.domain.repositories.SubscriptionRepository;
import com.wineinventory.paymentmanagement.external.paypal.PayPalWebhookValidator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/api/paypal/webhook")
public class PayPalWebhookController {

    private final SubscriptionRepository subscriptionRepository;
    private final PayPalWebhookValidator validator;

    public PayPalWebhookController(
            SubscriptionRepository subscriptionRepository,
            PayPalWebhookValidator validator) {
        this.subscriptionRepository = subscriptionRepository;
        this.validator = validator;
    }

    @PostMapping
    public ResponseEntity<String> handleWebhook(
            @RequestHeader HttpHeaders headers,
            @RequestBody Map<String, Object> event) {

        // 1. VALIDAR WEBHOOK
        boolean valid = validator.isValid(headers, event);
        if (!valid) {
            return ResponseEntity.status(400).body("Invalid webhook signature");
        }

        // 2. LEER EVENTO
        String eventType = (String) event.get("event_type");
        Map<String, Object> resource = (Map<String, Object>) event.get("resource");
        String subscriptionId = (String) resource.get("id");

        switch (eventType) {

            // -----------------------------
            // NUEVO: Al crear la suscripción
            // -----------------------------
            case "BILLING.SUBSCRIPTION.CREATED" -> {
                subscriptionRepository.findByPaypalSubscriptionId(subscriptionId)
                        .ifPresent(subscription -> {

                            subscription.activate(); // Entra en ACTIVE
                            subscriptionRepository.save(subscription);
                        });
                return ResponseEntity.ok("Subscription created event processed");
            }

            // -----------------------------
            // SUSCRIPCIÓN ACTIVADA
            // -----------------------------
            case "BILLING.SUBSCRIPTION.ACTIVATED" -> {

                // Next billing date viene en "billing_info.next_billing_time"
                Map<String, Object> billingInfo =
                        (Map<String, Object>) resource.get("billing_info");

                LocalDateTime nextBilling = null;

                if (billingInfo != null && billingInfo.get("next_billing_time") != null) {
                    String dateStr = (String) billingInfo.get("next_billing_time");
                    nextBilling = LocalDateTime.parse(
                            dateStr.replace("Z", ""),
                            DateTimeFormatter.ISO_DATE_TIME
                    );
                }

                LocalDateTime finalNextBilling = nextBilling;

                subscriptionRepository.findByPaypalSubscriptionId(subscriptionId)
                        .ifPresent(subscription -> {
                            subscription.activate();
                            if (finalNextBilling != null) {
                                subscription.setNextBillingDate(finalNextBilling);
                            }
                            subscriptionRepository.save(subscription);
                        });

                return ResponseEntity.ok("Subscription activated");
            }

            // -----------------------------
            // NUEVO: Primer pago completado
            // -----------------------------
            case "PAYMENT.SALE.COMPLETED" -> {

                // Este evento llega con "billing_agreement_id"
                String agreementId = (String) resource.get("billing_agreement_id");

                subscriptionRepository.findByPaypalSubscriptionId(agreementId)
                        .ifPresent(subscription -> {

                            // Si por alguna razón no está activo, lo activamos.
                            subscription.activate();

                            subscriptionRepository.save(subscription);
                        });

                return ResponseEntity.ok("Payment completed and subscription updated");
            }

            // -----------------------------
            // CANCELACIÓN
            // -----------------------------
            case "BILLING.SUBSCRIPTION.CANCELLED" -> {
                subscriptionRepository.findByPaypalSubscriptionId(subscriptionId)
                        .ifPresent(subscription -> {
                            subscription.cancel();
                            subscriptionRepository.save(subscription);
                        });

                return ResponseEntity.ok("Subscription cancelled");
            }

            default -> {
                return ResponseEntity.ok("Event ignored: " + eventType);
            }
        }
    }
}
