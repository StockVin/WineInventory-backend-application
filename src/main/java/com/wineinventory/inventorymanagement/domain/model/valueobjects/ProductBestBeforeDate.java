package com.wineinventory.inventorymanagement.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.time.LocalDate;

/**
 * Represents the best before date of a product in the inventory management system.
 * This value object encapsulates the date and provides validation to ensure it is a future date.
 *
 */
@Embeddable
public record ProductBestBeforeDate(LocalDate bestBeforeDate) {

    /**
     * This constructor validates the input parameter to ensure that it is a future date.
     *
     * @param bestBeforeDate The best before date of the product.
     * @throws IllegalArgumentException if the date is null or not a future date.
     */
    public ProductBestBeforeDate {
        if (bestBeforeDate == null || !bestBeforeDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Best before date must be a future date.");
        }
    }
}