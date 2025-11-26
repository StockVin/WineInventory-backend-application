package com.wineinventory.paymentandsubscriptions.application.internal.queryservices;

import com.wineinventory.paymentandsubscriptions.domain.model.entities.Plan;
import com.wineinventory.paymentandsubscriptions.domain.repositories.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanQueryServiceImpl implements PlanQueryService {

    private final PlanRepository planRepository;

    public PlanQueryServiceImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public List<Plan> handle() {
        return planRepository.findAll();
    }
}
