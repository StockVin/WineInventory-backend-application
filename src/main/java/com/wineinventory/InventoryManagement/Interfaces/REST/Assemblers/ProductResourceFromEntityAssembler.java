package com.wineinventory.InventoryManagement.Interfaces.REST.Assemblers;

import com.wineinventory.InventoryManagement.Domain.Model.Aggregates.Product;
import com.wineinventory.InventoryManagement.Interfaces.REST.Resources.ProductResource;

/**
 * This class is responsible for transforming a Product entity into a ProductResource.
 */
public class ProductResourceFromEntityAssembler {

    /**
     * This method transforms a Product entity into a ProductResource.
     * @param entity The Product entity to be transformed into a ProductResource.
     * @return A ProductResource that contains the details of the product.
     */
    public static ProductResource toResourceFromEntity(Product entity) {
        return new ProductResource(
                entity.getProductId(),
                entity.getImageUrl().imageUrl(),
                entity.getProductName().name(),
                entity.getBrandName().name(),
                entity.getLiquorType().name(),
                entity.getUnitPrice().amount(),
                entity.getMinimumStock().minimumStock(),
                entity.getAccountId().accountId()
        );
    }
}