package com.wineinventory.paymentandsubscriptions.application.internal.queryservices;

import com.wineinventory.paymentandsubscriptions.domain.model.entities.Plan;

import java.util.List;

public interface PlanQueryService {

    List<Plan> handle();
}
