package com.wineinventory.InventoryManagement.Domain.Model.ValueObjects;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public record ImageUrl(String value) {
    public ImageUrl {
        Objects.requireNonNull(value, "Image URL no puede ser nulo.");
    }
}