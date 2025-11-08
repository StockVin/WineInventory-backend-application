package com.wineinventory.InventoryManagement.Domain.Services;

import com.wineinventory.InventoryManagement.Domain.Model.Aggregates.Product;
import com.wineinventory.InventoryManagement.Domain.Model.Queries.*;

import java.util.List;
import java.util.Optional;

/**
 * ProductQueryService is an interface that defines methods for handling queries related to products.
 *
 * @summary
 * Service interface for handling queries related to products.
 *
 * @since 1.0.0
 */
public interface ProductQueryService {

    List<Product> handle(GetAllProductsByAccountIdQuery query);
    List<Product> handle(GetAllProductsByProviderIdAndWarehouseIdQuery query);
    List<Product> handle(GetAllProductsByWarehouseIdQuery query);

    Optional<Product> handle(GetProductByFullNameAndWarehouseIdQuery query);
    Optional<Product> handle(GetProductByIdAndWarehouseIdAndBestBeforeDateQuery query);
    Optional<Product> handle(GetProductByIdQuery query);
}