package com.wineinventory.paymentandsubscriptions.application.internal.commandservices;

import com.wineinventory.paymentandsubscriptions.domain.model.aggregates.Subscription;
import com.wineinventory.paymentandsubscriptions.domain.model.commands.CreateSubscriptionCommand;
import com.wineinventory.paymentandsubscriptions.domain.model.commands.CancelSubscriptionCommand;
import com.wineinventory.paymentandsubscriptions.domain.model.commands.WebhookPaymentCommand;

public interface SubscriptionCommandService {

    Subscription handle(CreateSubscriptionCommand command);

    Subscription handle(CancelSubscriptionCommand command);
    
    void handle(WebhookPaymentCommand command);
}
