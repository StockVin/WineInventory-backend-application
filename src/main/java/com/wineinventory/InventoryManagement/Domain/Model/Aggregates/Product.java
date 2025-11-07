package com.wineinventory.Domain.Model.Aggregates;

import com.wineinventory.InventoryManagement.Domain.Model.ValueObjects.AccountId;

import com.wineinventory.InventoryManagement.Domain.Model.ValueObjects.ImageUrl;
import com.wineinventory.Domain.Model.ValueObjects.LiquorType;
import com.wineinventory.Domain.Exceptions.ProductFailedCreationException;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "products")
public class Product {

    @Id
    private String id; 
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "product_name"))
    })
    private String name;
    
    @Enumerated(EnumType.STRING)
    private LiquorType type;
    
    @Embedded
    @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "product_price"))
    })
    private Double price;
    private LocalDate expirationDate;
    private int currentStock;
    private int minStockLevel;
    private String location;
    
    @Embedded
    @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "image_url"))
    })
    private ImageUrl imageUrl; 
    
    @Embedded
    private AccountId accountId; 

    protected Product() {} 

    public static Product create(String name, LiquorType type, double price, int initialStock, int minStock, String location, AccountId accountId, ImageUrl imageUrl, LocalDate expirationDate) {
        if (name == null || name.isBlank() || price <= 0 || initialStock < 0) {
            throw new ProductFailedCreationException("Datos de producto inválidos.");
        }
        
        Product product = new Product();
        product.id = UUID.randomUUID().toString(); 
        product.name = name;
        product.type = type;
        product.price = price;
        product.currentStock = initialStock;
        product.minStockLevel = minStock;
        product.location = location;
        product.accountId = accountId;
        product.imageUrl = imageUrl;
        product.expirationDate = expirationDate;
        return product;
    }
    
    public void decreaseStock(int quantity) {
        if (quantity <= 0 || this.currentStock < quantity) {
            throw new IllegalStateException("Stock insuficiente para el producto: " + name); 
        }
        this.currentStock -= quantity;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public LiquorType getType() { return type; }
    public double getPrice() { return price; }
    public LocalDate getExpirationDate() { return expirationDate; }
    public int getCurrentStock() { return currentStock; }
    public int getMinStockLevel() { return minStockLevel; }
    public String getLocation() { return location; }
    public ImageUrl getImageUrl() { return imageUrl; }
    public AccountId getAccountId() { return accountId; }
}