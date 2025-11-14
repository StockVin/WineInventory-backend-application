package com.wineinventory.inventorymanagement.interfaces.rest.resources;

import java.time.LocalDate;

/**
 * AddProductsToWarehouseResource is a record that represents an AddProductsToWarehouseCommand resource in the REST API.
 *
 * @summary
 * This record encapsulates the details of an AddProductsToWarehouseCommand.
 *
 * @since 1.0.0
 */
public record AddProductsToWarehouseResource(
        LocalDate bestBeforeDate,
        Integer quantity) {
}