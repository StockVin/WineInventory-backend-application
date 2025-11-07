package com.wineinventory.Domain.Model.Entities;

import java.time.Instant;
import java.util.UUID;

public class ProductExit {
    
    private final String logId;
    private final String productId;
    private final int quantity;
    private final String outputType;
    private final Instant timestamp;
    
    public ProductExit(String productId, int quantity, String outputType) {
        this.logId = UUID.randomUUID().toString();
        this.productId = productId;
        this.quantity = quantity;
        this.outputType = outputType;
        this.timestamp = Instant.now();
    }

    // Getters
    public String getLogId() { return logId; }
    public String getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public String getOutputType() { return outputType; }
    public Instant getTimestamp() { return timestamp; }
}