package com.wineinventory.InventoryManagement.Interfaces.REST.Assemblers;

import com.wineinventory.InventoryManagement.Domain.Model.Commands.UpdateProductMinimumStockCommand;
import com.wineinventory.InventoryManagement.Interfaces.REST.Resources.UpdateProductMinimumStockResource;

/**
 * This class is responsible for transforming an UpdateProductMinimumStockCommandFromResource into an UpdateProductMinimumStockCommand.
 */
public class UpdateProductMinimumStockCommandFromResourceAssembler {

    /**
     * Method to transform an UpdateProductMinimumStockCommandFromResource into an UpdateProductMinimumStockCommand.
     * @param resource The UpdateProductMinimumStockCommandFromResource to transform.
     * @param productId The Product ID of the product whose minimum stock level will be updated.
     * @return The Created UpdateProductMinimumStockCommand command.
     */
    public static UpdateProductMinimumStockCommand toCommandFromResource(UpdateProductMinimumStockResource resource, Long productId) {
        return new UpdateProductMinimumStockCommand(
                productId,
                resource.updatedMinimumStock()
        );
    }
}