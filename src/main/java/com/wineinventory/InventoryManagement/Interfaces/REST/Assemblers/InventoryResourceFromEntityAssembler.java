package com.wineinventory.InventoryManagement.Interfaces.REST.Assemblers;

import com.wineinventory.InventoryManagement.Domain.Model.Aggregates.Inventory;
import com.wineinventory.InventoryManagement.Interfaces.REST.Resources.InventoryResource;

/**
 * This class is responsible for transforming an Inventory entity to an InventoryResource.
 */
public class InventoryResourceFromEntityAssembler {

    /**
     * Transforms an Inventory entity to an InventoryResource.
     * @param entity The entity to be transformed.
     * @return The created InventoryResource.
     */
    public static InventoryResource toResourceFromEntity(Inventory entity) {
        return new InventoryResource(
                entity.getInventoryId(),
                entity.getProduct().getProductId(),
                entity.getWarehouse().getWarehouseId(),
                entity.getProductBestBeforeDate().bestBeforeDate(),
                entity.getProductStock().getStock(),
                entity.getProductState().toString()
        );
    }
}