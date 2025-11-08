package com.wineinventory.InventoryManagement.Interfaces.REST.Controllers;

import com.wineinventory.InventoryManagement.Domain.Services.ProductQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for Warehouse Products.
 * @summary
 * This class provides REST endpoints for warehouse products.
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "api/v1/warehouses/{warehouseId}/products", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Warehouses")
public class WarehouseProductsController {

    private final ProductQueryService productQueryService;

    public WarehouseProductsController(ProductQueryService productQueryService) {
        this.productQueryService = productQueryService;
    }
}