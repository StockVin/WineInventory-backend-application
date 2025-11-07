package com.wineinventory.InventoryManagement.Interfaces.REST.Assemblers;

import com.wineinventory.InventoryManagement.Domain.Model.Commands.UpdateProductCommand;
import com.wineinventory.InventoryManagement.Interfaces.REST.Resources.UpdateProductResource;

/**
 * This class is responsible for transforming an UpdateProductResource into an UpdateProductCommand.
 */
public class UpdateProductCommandFromResourceAssembler {

    /**
     * Method to transform an UpdateProductResource into an UpdateProductCommand.
     * @param resource The UpdateProductResource containing the product details to be updated.
     * @param productId The identifier of the product that its information will be updated.
     * @return The UpdateProductCommand that encapsulates the product update operation.
     */
    public static UpdateProductCommand toCommandFromResource(UpdateProductResource resource, Long productId) {
        return new UpdateProductCommand(
                productId,
                resource.name(),
                resource.brand(),
                resource.liquorType(),
                resource.updatedUnitPriceAmount(),
                resource.updatedMinimumStock(),
                resource.updatedImage()
        );
    }
}