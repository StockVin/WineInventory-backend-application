package com.wineinventory.InventoryManagement.Interfaces.REST.Resources;

import java.time.LocalDate;
import java.util.Date;

/**
 * DecreaseStockFromProductResource is a record that represents a DecreaseStockFromProductCommand resource in the REST API.
 *
 * @summary
 * This record encapsulates the details of a DecreaseStockFromProductResource.
 *
 * @since 1.0.0
 */
public record DecreaseStockFromProductResource(
        LocalDate expirationDate,
        Integer removedQuantity
) {
}