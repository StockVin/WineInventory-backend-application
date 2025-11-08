package com.wineinventory.InventoryManagement.Interfaces.REST.Assemblers;

import com.wineinventory.InventoryManagement.Domain.Model.Commands.MoveProductToAnotherWarehouseCommand;
import com.wineinventory.InventoryManagement.Interfaces.REST.Resources.MoveProductStockToAnotherWarehouseResource;

/**
 * This class is responsible for transforming a MoveProductsToAnotherWarehouseResource into a MoveProductsToAnotherWarehouseCommand.
 */
public class MoveProductsToAnotherWarehouseCommandFromResourceAssembler {

    /**
     * This method transforms a MoveProductsToAnotherWarehouseResource into a MoveProductsToAnotherWarehouseCommand.
     * @param resource The MoveProductsToAnotherWarehouseResource to be transformed into a command.
     * @param productId The identifier of the product that will be moved to another warehouse.
     * @param oldWarehouseId The identifier of the warehouse that currently stores the stock of the product that will be moved to another warehouse.
     * @return The MoveProductsToAnotherWarehouseCommand created from the resource.
     */
    public static MoveProductToAnotherWarehouseCommand toCommandFromResource(MoveProductStockToAnotherWarehouseResource resource, Long productId, Long oldWarehouseId) {
        return new MoveProductToAnotherWarehouseCommand(
                productId,
                oldWarehouseId,
                resource.newWarehouseId(),
                resource.movedStockExpirationDate(),
                resource.movedQuantity()
        );
    }
}