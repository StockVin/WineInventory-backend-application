package com.wineinventory.InventoryManagement.Application.Services;

import com.wineinventory.InventoryManagement.Application.Queries.Dtos.InventoryItemDto;

import com.wineinventory.InventoryManagement.Application.Queries.Dtos.InventoryItemDto;
import com.wineinventory.InventoryManagement.Domain.Repositories.ProductRepository;

import com.wineinventory.Application.Queries.GetAllProductsByAccountIdQuery;
import com.wineinventory.InventoryManagement.Domain.Model.ValueObjects.AccountId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductQueryService {
    private final ProductRepository productRepository;

    public ProductQueryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<InventoryItemDto> handle(GetAllProductsByAccountIdQuery query) {
        AccountId accountId = new AccountId(query.accountId());

        return productRepository.findAllByAccountId(accountId).stream()
            .map(InventoryItemDto::fromEntity) 
            .collect(Collectors.toList());
    }
}