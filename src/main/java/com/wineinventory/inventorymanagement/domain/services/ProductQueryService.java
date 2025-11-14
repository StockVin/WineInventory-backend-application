package com.wineinventory.inventorymanagement.domain.services;

import com.wineinventory.inventorymanagement.domain.model.aggregates.Product;
import com.wineinventory.inventorymanagement.domain.model.queries.*;

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