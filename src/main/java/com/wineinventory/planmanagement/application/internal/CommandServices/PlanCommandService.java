package com.wineinventory.planmanagement.application.internal.CommandServices;

import com.wineinventory.planmanagement.domain.model.aggregates.Plan;
import com.wineinventory.planmanagement.domain.model.commands.CreatePlanCommand;

public interface PlanCommandService {

    Plan handle(CreatePlanCommand command);
}
