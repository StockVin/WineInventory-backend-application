package com.wineinventory.InventoryManagement.Interfaces.REST.Assemblers;

import com.wineinventory.InventoryManagement.Domain.Model.Aggregates.Inventory;
import com.wineinventory.InventoryManagement.Interfaces.REST.Resources.ProductInventoryResource;

public class ProductInventoryResourceFromEntityAssembler {

    public static ProductInventoryResource toResourceFromEntity(Inventory inventory) {

        var product = inventory.getProduct();

        return new ProductInventoryResource(
                product.getProductId(),
                product.getProductName().name(),
                product.getBrandName().name(),
                product.getUnitPrice().amount(),
                product.getMinimumStock().getMinimumStock(),
                inventory.getProductStock().getStock(),
                inventory.getProductState().name(),
                inventory.getProductBestBeforeDate().bestBeforeDate());

    }
}