package com.wineinventory.inventorymanagement.interfaces.rest.assemblers;

import com.wineinventory.inventorymanagement.domain.model.commands.AddProductsToWarehouseCommand;
import com.wineinventory.inventorymanagement.interfaces.rest.resources.AddProductsToWarehouseResource;

/**
 * This class is responsible for transforming an AddProductsToWarehouseResource into an AddProductsToWarehouseCommand.
 *
 * @since 1.0.0
 */
public class AddProductsToWarehouseCommandFromResourceAssembler {

    /**
     * Method to transform an AddProductsToWarehouseResource into an AddProductsToWarehouseCommand.
     * @param resource The AddProductsToWarehouseResource to transform.
     * @param productId The Product ID of the product where the stock will be added.
     * @param warehouseId The Warehouse ID that contains the product whose stock will increment.
     * @return The AddProductsToWarehouseCommand created from the resource.
     */
    public static AddProductsToWarehouseCommand toCommandFromResource(AddProductsToWarehouseResource resource, Long productId, Long warehouseId) {

        return new AddProductsToWarehouseCommand(
                productId,
                warehouseId,
                resource.bestBeforeDate(),
                resource.quantity()
        );
    }
}