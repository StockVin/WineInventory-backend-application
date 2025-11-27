package com.wineinventory.paymentandsubscriptions.infrastructure.config;

import com.wineinventory.paymentandsubscriptions.domain.model.entities.Plan;
import com.wineinventory.paymentandsubscriptions.domain.model.valueobjects.PlanType;
import com.wineinventory.paymentandsubscriptions.domain.model.valueobjects.PlanLimits;
import com.wineinventory.paymentandsubscriptions.domain.repositories.PlanRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class PlanDataLoader implements CommandLineRunner {

    private final PlanRepository planRepository;

    public PlanDataLoader(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            List<Plan> existingPlans = planRepository.findAll();
            if (existingPlans.isEmpty()) {
                loadPlans();
            } else {
                // Migrate legacy literal planIds to UUIDs
                boolean changed = false;
                for (Plan p : existingPlans) {
                    String pid = p.getPlanId();
                    if (pid != null && (pid.endsWith("-plan-id") || pid.equalsIgnoreCase("free") || pid.equalsIgnoreCase("plus") || pid.equalsIgnoreCase("pro"))) {
                        String newId = UUID.randomUUID().toString();
                        p.update(
                                newId,
                                p.getPaypalPlanId(),
                                p.getPaypalSubscriptionId(),
                                p.getPlanType(),
                                p.getDescription(),
                                p.getPaymentFrequency(),
                                p.getPrice(),
                                p.getCurrency(),
                                p.getMaxProducts()
                        );
                        planRepository.save(p);
                        changed = true;
                    }
                }
                if (!changed) {
                    // no-op
                }
            }
        } catch (Exception e) {
            loadPlans();
        }
    }

    private void loadPlans() {
        PlanLimits freeLimits = PlanLimits.forType(PlanType.Free);
        Plan freePlan = new Plan(
            UUID.randomUUID().toString(),
            null, // PayPal plan ID - debe configurarse manualmente en producción
            null, // PayPal subscription ID - no aplica para plan gratuito
            PlanType.Free.name(),
            "Perfect for small businesses getting started",
            "None",
            0.0,
            "PEN",
            freeLimits.getMaxProducts()
        );
        planRepository.save(freePlan);

        PlanLimits plusLimits = PlanLimits.forType(PlanType.Plus);
        Plan plusPlan = new Plan(
            UUID.randomUUID().toString(),
            null, // PayPal plan ID - debe configurarse manualmente en producción
            "I-JDYVJ1D8XSWY", // PayPal subscription ID fijo para este plan
            PlanType.Plus.name(),
            "Ideal for growing businesses",
            "Monthly",
            19.90,
            "PEN",
            plusLimits.getMaxProducts()
        );
        planRepository.save(plusPlan);

        PlanLimits proLimits = PlanLimits.forType(PlanType.Pro);
        Plan proPlan = new Plan(
            UUID.randomUUID().toString(),
            null, // PayPal plan ID - debe configurarse manualmente en producción
            "I-JDYVJ1D8XSWY", // PayPal subscription ID fijo para este plan
            PlanType.Pro.name(),
            "For businesses with advanced needs",
            "Monthly",
            29.90,
            "PEN",
            proLimits.getMaxProducts()
        );
        planRepository.save(proPlan);
    }
}
