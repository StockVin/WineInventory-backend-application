package com.wineinventory.paymentandsubscriptions.interfaces.rest.resources;

public record PaymentPreferenceResource(
    String preferenceId,
    String initPoint,
    String message
) {
    public static PaymentPreferenceResource of(String preferenceId, String initPoint, String message) {
        return new PaymentPreferenceResource(preferenceId, initPoint, message);
    }
}
