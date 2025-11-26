package com.wineinventory.paymentandsubscriptions.interfaces.rest.assemblers;

import com.wineinventory.paymentandsubscriptions.domain.model.commands.WebhookPaymentCommand;

public class WebhookPaymentCommandFromResourceAssembler {
    
    public static WebhookPaymentCommand toCommandFromResource(String paymentId) {
        return new WebhookPaymentCommand(paymentId);
    }
}
