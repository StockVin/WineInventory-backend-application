package com.wineinventory.planmanagement.application.internal.QueryServices;

import com.wineinventory.planmanagement.domain.model.aggregates.Plan;

import java.util.List;

public interface PlanQueryService {

    List<Plan> handle();

    Plan handle(Long id);
}
