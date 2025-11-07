package com.wineinventory.InventoryManagement.Domain.Exceptions;

public class ProductFailedCreationException extends RuntimeException {
    public ProductFailedCreationException(String message) {
        super(message);
    }
}