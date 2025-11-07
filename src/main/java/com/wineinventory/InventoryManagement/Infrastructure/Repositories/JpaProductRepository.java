package com.wineinventory.Infrastructure.Repositories;
import com.wineinventory.InventoryManagement.Domain.Repositories.ProductRepository;

import com.wineinventory.Domain.Model.Aggregates.Product;
import com.wineinventory.InventoryManagement.Domain.Model.ValueObjects.AccountId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JpaProductRepository extends JpaRepository<Product, String>, ProductRepository {

    @Override
    List<Product> findAllByAccountId(AccountId accountId); 
}