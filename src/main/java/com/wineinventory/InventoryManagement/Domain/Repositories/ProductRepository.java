package com.wineinventory.InventoryManagement.Domain.Repositories;

import com.wineinventory.Domain.Model.Aggregates.Product;
import com.wineinventory.InventoryManagement.Domain.Model.ValueObjects.AccountId;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    
    Optional<Product> findById(String id);
    Product save(Product product);
    void deleteById(String id);
    
    List<Product> findAllByAccountId(AccountId accountId);
}