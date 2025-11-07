package com.wineinventory.InventoryManagement.Domain.Model.ValueObjects;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public record AccountId(Long value) {
    public AccountId {
        Objects.requireNonNull(value, "Account ID no puede ser nulo.");
    }
}