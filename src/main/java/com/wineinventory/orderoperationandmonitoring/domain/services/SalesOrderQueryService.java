package com.wineinventory.orderoperationandmonitoring.domain.services;

import com.wineinventory.orderoperationandmonitoring.domain.model.aggregates.SalesOrder;
import com.wineinventory.orderoperationandmonitoring.domain.model.queries.GetAllSalesOrdersByBuyerIdQuery;

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
