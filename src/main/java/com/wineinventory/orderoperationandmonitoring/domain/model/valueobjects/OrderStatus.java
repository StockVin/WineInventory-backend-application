package com.wineinventory.orderoperationandmonitoring.domain.model.valueobjects;

/**
 * Enumera los posibles estados del ciclo de vida de una orden de venta.
 */
public enum OrderStatus {
    PENDING,
    PROCESSING,
    COMPLETED,
    CANCELLED
}
