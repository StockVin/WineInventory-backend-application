package com.wineinventory.planmanagement.application.internal.CommandServices;

import com.wineinventory.planmanagement.domain.model.aggregates.Plan;
import com.wineinventory.planmanagement.domain.model.commands.CreatePlanCommand;
import com.wineinventory.planmanagement.domain.repositories.PlanRepository;
import org.springframework.stereotype.Service;

@Service
public class PlanCommandServiceImpl implements PlanCommandService {

    private final PlanRepository planRepository;

    public PlanCommandServiceImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public Plan handle(CreatePlanCommand command) {
        Plan plan = new Plan(
                command.code(),
                command.name(),
                command.description(),
                command.price(),
                command.currency(),
                command.paypalPlanId()
        );
        return planRepository.save(plan);
    }
}
