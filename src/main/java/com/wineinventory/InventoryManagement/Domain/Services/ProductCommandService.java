package com.wineinventory.InventoryManagement.Domain.Services;

import com.wineinventory.InventoryManagement.Domain.Model.Aggregates.Product;
import com.wineinventory.InventoryManagement.Domain.Model.Commands.*;

import java.util.Optional;

/**
 * ProductCommandService is an interface that defines methods for handling commands related to products.
 *
 * @summary
 * Service interface for handling commands related to products.
 *
 * @since 1.0.0
 */
public interface ProductCommandService {

    Optional<Product> handle(UpdateProductCommand command);
    Optional<Product> handle(CreateProductCommand command);
    Optional<Product> handle(UpdateProductMinimumStockCommand command);

    void handle(DeleteProductCommand command);
}