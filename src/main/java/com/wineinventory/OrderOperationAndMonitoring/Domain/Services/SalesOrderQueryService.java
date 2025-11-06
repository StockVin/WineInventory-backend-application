package com.wineinventory.OrderOperationAndMonitoring.Domain.Services;

import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Aggregates.SalesOrder;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Queries.GetAllSalesOrdersByBuyerIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Contrato de la capa de dominio para las operaciones de lectura sobre las órdenes de venta.
 */
public interface SalesOrderQueryService {

    List<SalesOrder> handle(GetAllSalesOrdersByBuyerIdQuery query);

    Optional<SalesOrder> getById(Long orderId);

    List<SalesOrder> getAll();
}
