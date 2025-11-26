package com.wineinventory.paymentandsubscriptions.application.internal.outboundservices.paymentproviders.models;

import java.math.BigDecimal;

public class PaypalOrderItem {
    
    private String name;
    private String description;
    private String sku;
    private BigDecimal unitAmount;
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;
    private Integer quantity;
    private String category;
    
    public PaypalOrderItem() {}
    
    public PaypalOrderItem(String name, String description, String sku, 
                         BigDecimal unitAmount, BigDecimal taxAmount, 
                         Integer quantity, String category) {
        this.name = name;
        this.description = description;
        this.sku = sku;
        this.unitAmount = unitAmount;
        this.taxAmount = taxAmount;
        this.quantity = quantity;
        this.category = category;
        this.totalAmount = unitAmount.add(taxAmount).multiply(BigDecimal.valueOf(quantity));
    }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    
    public BigDecimal getUnitAmount() { return unitAmount; }
    public void setUnitAmount(BigDecimal unitAmount) { 
        this.unitAmount = unitAmount;
        recalculateTotal();
    }
    
    public BigDecimal getTaxAmount() { return taxAmount; }
    public void setTaxAmount(BigDecimal taxAmount) { 
        this.taxAmount = taxAmount;
        recalculateTotal();
    }
    
    public BigDecimal getTotalAmount() { return totalAmount; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { 
        this.quantity = quantity;
        recalculateTotal();
    }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    // Business method
    private void recalculateTotal() {
        if (unitAmount != null && taxAmount != null && quantity != null) {
            this.totalAmount = unitAmount.add(taxAmount).multiply(BigDecimal.valueOf(quantity));
        }
    }
}
