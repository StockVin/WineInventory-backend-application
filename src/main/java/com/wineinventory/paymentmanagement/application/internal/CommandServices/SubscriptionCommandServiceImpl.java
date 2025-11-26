package com.wineinventory.paymentmanagement.application.internal.CommandServices;

import com.wineinventory.paymentmanagement.domain.model.aggregates.Subscription;
import com.wineinventory.paymentmanagement.domain.model.commands.CreateSubscriptionCommand;
import com.wineinventory.paymentmanagement.domain.model.commands.CancelSubscriptionCommand;
import com.wineinventory.paymentmanagement.domain.repositories.SubscriptionRepository;
import com.wineinventory.paymentmanagement.external.paypal.PayPalSubscriptionService;
import com.wineinventory.paymentmanagement.external.paypal.dtos.PayPalCreateSubscriptionResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SubscriptionCommandServiceImpl implements SubscriptionCommandService {

    private final SubscriptionRepository subscriptionRepository;
    private final PayPalSubscriptionService payPalSubscriptionService;

    public SubscriptionCommandServiceImpl(
            SubscriptionRepository subscriptionRepository,
            PayPalSubscriptionService payPalSubscriptionService) {
        this.subscriptionRepository = subscriptionRepository;
        this.payPalSubscriptionService = payPalSubscriptionService;
    }

    /** ----------------------------------------------------------
     *   FUNCIÓN: EXTRAE EL LINK DE APROBACIÓN DE PAYPAL
     * ---------------------------------------------------------- */
    private String extractApprovalLink(PayPalCreateSubscriptionResponse response) {
        return response.links().stream()
                .filter(link -> link.rel().equals("approve"))
                .findFirst()
                .map(PayPalCreateSubscriptionResponse.PayPalLink::href)
                .orElse(null);
    }

    /** ----------------------------------------------------------
     *   MÉTODO PRINCIPAL: CREAR SUSCRIPCIÓN (COMPLETE VERSION)
     * ---------------------------------------------------------- */
    @Override
    public Subscription handle(CreateSubscriptionCommand command) {

        // 1. Crear suscripción en PayPal
        PayPalCreateSubscriptionResponse response =
                payPalSubscriptionService.createSubscription(command.paypalPlanId());

        // 2. ID REAL de PayPal
        String paypalSubscriptionId = response.id();

        // 3. Estado inicial (ej. "APPROVAL_PENDING")
        String status = response.status();

        // 4. Link para aprobar la suscripción (frontend lo usa)
        String approvalLink = extractApprovalLink(response);

        // 5. Crear suscripción interna (base de datos)
        Subscription subscription = new Subscription(
                command.userId(),
                command.planId(),
                paypalSubscriptionId,
                status,
                command.currency(),
                command.amount(),
                LocalDateTime.now(),
                null
        );

        // 6. Guardar en DB
        subscriptionRepository.save(subscription);

        // 7. IMPORTANTE: Guardar link temporalmente en el objeto (opcional)
        //    Si no quieres almacenar en BD, solo devuélvelo desde controller.

        return subscription;
    }

    /** ----------------------------------------------------------
     *   CANCELAR SUSCRIPCIÓN
     * ---------------------------------------------------------- */
    @Override
    public Subscription handle(CancelSubscriptionCommand command) {
        return subscriptionRepository.findById(command.subscriptionId())
                .map(subscription -> {
                    subscription.cancel();
                    return subscriptionRepository.save(subscription);
                })
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found"));
    }
}
