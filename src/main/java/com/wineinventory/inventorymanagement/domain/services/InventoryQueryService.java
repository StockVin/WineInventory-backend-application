package com.wineinventory.inventorymanagement.domain.services;

import com.wineinventory.inventorymanagement.domain.model.aggregates.Inventory;
import com.wineinventory.inventorymanagement.domain.model.queries.GetAllProductsByWarehouseIdQuery;
import com.wineinventory.inventorymanagement.domain.model.queries.GetInventoryByIdQuery;
import com.wineinventory.inventorymanagement.domain.model.queries.GetInventoryByProductIdAndWarehouseIdAndBestBeforeDateQuery;
import com.wineinventory.inventorymanagement.domain.model.queries.GetInventoryByProductIdAndWarehouseIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * InventoryQueryService is an interface that defines methods for handling queries related to inventories.
 *
 * @summary
 * Service interface for handling queries related to inventories.
 *
 * @since 1.0.0
 */
public interface InventoryQueryService {

    Optional<Inventory> handle(GetInventoryByIdQuery query);
    Optional<Inventory> handle(GetInventoryByProductIdAndWarehouseIdAndBestBeforeDateQuery query);
    Optional<Inventory> handle(GetInventoryByProductIdAndWarehouseIdQuery query);
    List<Inventory> handle(GetAllProductsByWarehouseIdQuery query);
}