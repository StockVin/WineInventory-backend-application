package com.wineinventory.orderoperationandmonitoring.domain.services;

import com.wineinventory.orderoperationandmonitoring.domain.model.aggregates.SalesOrder;
import com.wineinventory.orderoperationandmonitoring.domain.model.commands.GenerateSalesOrderCommand;
import com.wineinventory.orderoperationandmonitoring.domain.model.valueobjects.OrderStatus;

/**
 * Define las operaciones de escritura que la capa de aplicación expone para gestionar órdenes de venta.
 */
public interface SalesOrderCommandService {
    SalesOrder handle(GenerateSalesOrderCommand command);

    SalesOrder updateStatus(Long orderId, OrderStatus newStatus);

    void delete(Long orderId);
}
