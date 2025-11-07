package com.wineinventory.Application.Commands;

import com.wineinventory.Domain.Model.ValueObjects.LiquorType;
import java.time.LocalDate;

public record CreateProductCommand(
        String name,
        LiquorType type,
        double price,
        LocalDate expirationDate,
        int currentStock,
        int minStockLevel,
        String location,
        String imageUrl,
        Long accountId 
) {
}