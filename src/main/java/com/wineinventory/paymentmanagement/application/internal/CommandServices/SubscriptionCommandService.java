package com.wineinventory.paymentmanagement.application.internal.CommandServices;

import com.wineinventory.paymentmanagement.domain.model.aggregates.Subscription;
import com.wineinventory.paymentmanagement.domain.model.commands.CreateSubscriptionCommand;
import com.wineinventory.paymentmanagement.domain.model.commands.CancelSubscriptionCommand;

public interface SubscriptionCommandService {

    Subscription handle(CreateSubscriptionCommand command);

    Subscription handle(CancelSubscriptionCommand command);
}
