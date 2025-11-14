package com.wineinventory.inventorymanagement.domain.exceptions;

public class ProductFailedCreationException extends RuntimeException {
    public ProductFailedCreationException(String message) {
        super(message);
    }
}