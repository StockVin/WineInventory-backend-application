package com.wineinventory.InventoryManagement.Domain.Services;

import com.wineinventory.InventoryManagement.Domain.Model.Aggregates.Inventory;
import com.wineinventory.InventoryManagement.Domain.Model.Commands.*;

import java.util.Optional;

/**
 * InventoryCommandService is an interface that defines methods for handling commands related to product inventories in a warehouse.
 *
 * @summary
 * Service interface for handling commands related to inventories.
 *
 * @since 1.0.0
 */
public interface InventoryCommandService {

    Optional<Inventory> handle(AddStockToProductCommand command);

    Optional<Inventory> handle(ReduceStockFromProductCommand command);

    Optional<Inventory> handle(AddProductsToWarehouseCommand command);

    Optional<Inventory> handle(MoveProductToAnotherWarehouseCommand command);

    Long handle(DeleteProductFromWarehouseCommand command);
}