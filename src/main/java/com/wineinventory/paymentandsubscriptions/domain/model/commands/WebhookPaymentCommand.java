package com.wineinventory.paymentandsubscriptions.domain.model.commands;

public record WebhookPaymentCommand(
    String paymentId
) {
    public WebhookPaymentCommand(String paymentId) {
        this.paymentId = paymentId;
    }
}
