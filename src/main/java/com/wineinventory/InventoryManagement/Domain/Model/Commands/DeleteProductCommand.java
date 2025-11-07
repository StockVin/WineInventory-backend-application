package com.wineinventory.InventoryManagement.Domain.Model.Commands;

/**
 * This command is used to delete a product only when it has 0 stock in all the warehouses.
 */
public record DeleteProductCommand(Long productId) {
}