package com.wineinventory.paymentandsubscriptions.interfaces.rest.assemblers;

public record SubscriptionAssembler(
    String subscriptionId,
    String planId,
    String status,
    String expirationDate,
    String planType,
    String paymentFrequency,
    Integer maxProducts,
    String providerSubscriptionId,
    String approvalUrl,
    String initPoint,
    String message
) {
    public static SubscriptionAssembler fromSubscriptionWithPayment(
            String subscriptionId, String planId, String status, String expirationDate,
            String planType, String paymentFrequency, Integer maxProducts,
            String providerSubscriptionId, String approvalUrl, String initPoint, String message) {
        return new SubscriptionAssembler(
                subscriptionId, planId, status, expirationDate, planType, paymentFrequency,
                maxProducts, providerSubscriptionId, approvalUrl, initPoint, message
        );
    }
    
    public static SubscriptionAssembler fromSubscription(
            String subscriptionId, String planId, String status, String expirationDate,
            String planType, String paymentFrequency, Integer maxProducts) {
        return new SubscriptionAssembler(
                subscriptionId, planId, status, expirationDate, planType, paymentFrequency,
                maxProducts, null, null, null, null
        );
    }
}
