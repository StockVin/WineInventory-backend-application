package com.wineinventory.Application.Services;

import com.wineinventory.InventoryManagement.Domain.Repositories.ProductRepository;

import com.wineinventory.Application.Commands.CreateProductCommand;
import com.wineinventory.Application.Commands.DeleteProductCommand;
import com.wineinventory.Domain.Model.Aggregates.Product;
import com.wineinventory.InventoryManagement.Domain.Model.ValueObjects.AccountId;
import com.wineinventory.InventoryManagement.Domain.Model.ValueObjects.ImageUrl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductCommandService {
    private final ProductRepository productRepository;

    public ProductCommandService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public String handle(CreateProductCommand command) {
        AccountId accountId = new AccountId(command.accountId());
        ImageUrl imageUrl = new ImageUrl(command.imageUrl());

        Product newProduct = Product.create(
            command.name(), command.type(), command.price(),
            command.currentStock(), command.minStockLevel(),
            command.location(), accountId, imageUrl, command.expirationDate()
        );

        productRepository.save(newProduct);
        return newProduct.getId();
    }

    @Transactional
    public void handle(DeleteProductCommand command) {
        productRepository.deleteById(command.productId());
    }
}