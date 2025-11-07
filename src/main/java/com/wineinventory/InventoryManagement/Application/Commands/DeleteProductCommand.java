package com.wineinventory.Application.Commands;

public record DeleteProductCommand(
        String productId,
        Long accountId 
) {
}