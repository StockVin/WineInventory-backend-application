package com.wineinventory.InventoryManagement.Interfaces.REST.Assemblers;

import com.wineinventory.InventoryManagement.Domain.Model.Commands.CreateProductCommand;
import com.wineinventory.InventoryManagement.Interfaces.REST.Resources.CreateProductResource;

/**
 * This class is responsible for transforming a CreateProductResource into a CreateProductCommand.
 */
public class CreateProductCommandFromResourceAssembler {

    /**
     * This method transforms a CreateProductResource into a CreateProductCommand.
     * @param resource The CreateProductResource to transform.
     * @return The CreateProductCommand created from the resource.
     */
    public static CreateProductCommand toCommandFromResource(CreateProductResource resource, Long accountId) {

        return new CreateProductCommand(
                resource.name(),
                resource.liquorType(),
                resource.brandName(),
                resource.unitPriceAmount(),
                resource.minimumStock(),
                resource.image(),
                accountId
        );
    }
}