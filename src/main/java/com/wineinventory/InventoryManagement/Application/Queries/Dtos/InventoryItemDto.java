package com.wineinventory.InventoryManagement.Application.Queries.Dtos;

import com.wineinventory.Domain.Model.Aggregates.Product;
import java.time.LocalDate;

public record InventoryItemDto(
        String id,
        String name,
        String type,
        double price,
        LocalDate expirationDate,
        int currentStock,
        int minStockLevel,
        String location,
        String imageUrl
) {
    public static InventoryItemDto fromEntity(Product product) {
        return new InventoryItemDto(
            product.getId(),
            product.getName(),
            product.getType().toString(),
            product.getPrice(),
            product.getExpirationDate(),
            product.getCurrentStock(),
            product.getMinStockLevel(),
            product.getLocation(),
            product.getImageUrl().value()
        );
    }
}