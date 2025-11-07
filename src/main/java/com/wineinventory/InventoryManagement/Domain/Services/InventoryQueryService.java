package com.wineinventory.InventoryManagement.Domain.Services;

import com.wineinventory.InventoryManagement.Domain.Model.Aggregates.Inventory;
import com.wineinventory.InventoryManagement.Domain.Model.Queries.GetAllProductsByWarehouseIdQuery;
import com.wineinventory.InventoryManagement.Domain.Model.Queries.GetInventoryByIdQuery;
import com.wineinventory.InventoryManagement.Domain.Model.Queries.GetInventoryByProductIdAndWarehouseIdAndBestBeforeDateQuery;
import com.wineinventory.InventoryManagement.Domain.Model.Queries.GetInventoryByProductIdAndWarehouseIdQuery;

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