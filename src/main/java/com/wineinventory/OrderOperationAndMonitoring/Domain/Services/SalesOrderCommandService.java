package com.wineinventory.OrderOperationAndMonitoring.Domain.Services;

import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Aggregates.SalesOrder;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Commands.GenerateSalesOrderCommand;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.ValueObjects.OrderStatus;

/**
 * Define las operaciones de escritura que la capa de aplicación expone para gestionar órdenes de venta.
 */
public interface SalesOrderCommandService {
    SalesOrder handle(GenerateSalesOrderCommand command);

    SalesOrder updateStatus(Long orderId, OrderStatus newStatus);

    void delete(Long orderId);
}
