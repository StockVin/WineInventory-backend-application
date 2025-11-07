package com.wineinventory.InventoryManagement.Domain.Services;

import com.wineinventory.InventoryManagement.Domain.Model.Aggregates.Warehouse;
import com.wineinventory.InventoryManagement.Domain.Model.Commands.CreateWarehouseCommand;
import com.wineinventory.InventoryManagement.Domain.Model.Commands.DeleteWarehouseCommand;
import com.wineinventory.InventoryManagement.Domain.Model.Commands.UpdateWarehouseCommand;
import com.wineinventory.InventoryManagement.Domain.Model.Commands.UploadImageCommand;

import java.util.Optional;

/**
 * WarehousesCommandService is an interface that defines methods for handling commands related to warehouses.
 *
 * @summary
 * Service interface for handling commands related to warehouses.
 *
 * @since 1.0.0
 */
public interface WarehouseCommandService {

    /**
     * Handles the creation of a new warehouse.
     *
     * @param warehouseCommand the command containing the details for creating a warehouse
     * @return an Optional containing the created Warehouse if successful, or empty if not
     * @see CreateWarehouseCommand
     * @see UploadImageCommand
     */
    Optional<Warehouse> handle(CreateWarehouseCommand warehouseCommand);

    /**
     * Handles the update of an existing warehouse.
     *
     * @param command the command containing the details for updating a warehouse
     * @return an Optional containing the updated Warehouse if successful, or empty if not
     * @see UpdateWarehouseCommand
     */
    Optional<Warehouse> handle(UpdateWarehouseCommand command);

    /**
     * Handles the deletion of a warehouse.
     *
     * @param command the command containing the details for deleting a warehouse
     */
    void handle(DeleteWarehouseCommand command);
}