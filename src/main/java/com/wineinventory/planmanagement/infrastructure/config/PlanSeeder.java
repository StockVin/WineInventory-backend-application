package com.wineinventory.planmanagement.infrastructure.config;

import com.wineinventory.planmanagement.domain.model.aggregates.Plan;
import com.wineinventory.planmanagement.domain.repositories.PlanRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class PlanSeeder {

    private final PlanRepository planRepository;

    public PlanSeeder(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @PostConstruct
    public void seedPlans() {

        if (planRepository.findAll().isEmpty()) {

            System.out.println("Seeding initial plans into database...");

            // FREE PLAN
            planRepository.save(new Plan(
                    "FREE",
                    "Free Plan",
                    "Free subscription plan with limited features",
                    0.0,
                    "USD",
                    null
            ));

            // PLUS PLAN
            planRepository.save(new Plan(
                    "PLUS",
                    "WineInventory Plus",
                    "Monthly Plus plan with advanced features",
                    19.90,
                    "USD",
                    "P-4W457603JE005083CNETBCWY"
            ));

            // PRO PLAN
            planRepository.save(new Plan(
                    "PRO",
                    "WineInventory Pro",
                    "Monthly Pro plan with all features",
                    29.90,
                    "USD",
                    "P-1C748781LL4369849NETBC3Q"
            ));

            System.out.println("Plans successfully seeded!");

        } else {
            System.out.println("ℹ Plans already exist. Seed skipped.");
        }
    }
}
