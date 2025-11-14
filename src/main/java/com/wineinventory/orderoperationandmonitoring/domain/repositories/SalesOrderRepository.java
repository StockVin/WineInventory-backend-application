package com.wineinventory.orderoperationandmonitoring.domain.repositories;

import com.wineinventory.orderoperationandmonitoring.domain.model.aggregates.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio Spring Data encargado de persistir y recuperar agregados de órdenes de venta.
 */
@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {

    List<SalesOrder> findAllByBuyerId(Long buyerId);

    Optional<SalesOrder> findByOrderNumber(String orderNumber);
}
