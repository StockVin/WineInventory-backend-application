package com.wineinventory.paymentandsubscriptions.application.internal.outboundservices.paymentproviders.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PaypalOrder {
    
    private String orderId;
    private String paypalOrderId;
    private BigDecimal totalAmount;
    private String currency;
    private String status;
    private List<PaypalOrderItem> items;
    private PaypalPaymentMethod paymentMethod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public PaypalOrder() {}
    
    public PaypalOrder(String orderId, String paypalOrderId, BigDecimal totalAmount, 
                      String currency, String status, List<PaypalOrderItem> items) {
        this.orderId = orderId;
        this.paypalOrderId = paypalOrderId;
        this.totalAmount = totalAmount;
        this.currency = currency;
        this.status = status;
        this.items = items;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    
    public String getPaypalOrderId() { return paypalOrderId; }
    public void setPaypalOrderId(String paypalOrderId) { this.paypalOrderId = paypalOrderId; }
    
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { 
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }
    
    public List<PaypalOrderItem> getItems() { return items; }
    public void setItems(List<PaypalOrderItem> items) { this.items = items; }
    
    public PaypalPaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaypalPaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public boolean isApproved() {
        return "APPROVED".equals(status);
    }
    
    public boolean isCompleted() {
        return "COMPLETED".equals(status);
    }
    
    public boolean isPending() {
        return "PENDING".equals(status);
    }
    
    public boolean isFailed() {
        return "FAILED".equals(status);
    }
}
