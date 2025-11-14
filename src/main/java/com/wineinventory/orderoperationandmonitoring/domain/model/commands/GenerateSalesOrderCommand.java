package com.wineinventory.orderoperationandmonitoring.domain.model.commands;

import com.wineinventory.orderoperationandmonitoring.domain.model.valueobjects.DeliveryInformation;
import com.wineinventory.orderoperationandmonitoring.domain.model.valueobjects.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Comando de aplicación que encapsula toda la información necesaria para crear
 * una nueva orden de venta desde la capa de interfaces.
 */
public record GenerateSalesOrderCommand(
        Long buyerId,
        String customerEmail,
        String currency,
        List<Item> items,
        DeliveryInformation deliveryInformation,
        LocalDateTime deliveryDate,
        OrderStatus initialStatus,
        Double taxAmount,
        String notes
) {


    public record Item(Long productId, String productName, Integer quantity, Double unitPrice) {
    }
}
