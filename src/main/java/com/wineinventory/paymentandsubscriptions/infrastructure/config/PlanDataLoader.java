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
            }
        } catch (Exception e) {
            loadPlans();
        }
    }

    private void loadPlans() {
        PlanLimits freeLimits = PlanLimits.forType(PlanType.Free);
        Plan freePlan = new Plan(
            UUID.randomUUID().toString(),
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
