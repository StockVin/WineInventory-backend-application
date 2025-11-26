package com.wineinventory.paymentandsubscriptions.application.internal.outboundservices.paymentproviders.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaypalPayment {
    
    private String paymentId;
    private String paypalPaymentId;
    private String paypalOrderId;
    private BigDecimal amount;
    private String currency;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public PaypalPayment() {}
    
    public PaypalPayment(String paymentId, String paypalPaymentId, String paypalOrderId, 
                        BigDecimal amount, String currency, String status) {
        this.paymentId = paymentId;
        this.paypalPaymentId = paypalPaymentId;
        this.paypalOrderId = paypalOrderId;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
    
    public String getPaypalPaymentId() { return paypalPaymentId; }
    public void setPaypalPaymentId(String paypalPaymentId) { this.paypalPaymentId = paypalPaymentId; }
    
    public String getPaypalOrderId() { return paypalOrderId; }
    public void setPaypalOrderId(String paypalOrderId) { this.paypalOrderId = paypalOrderId; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { 
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    // Business methods
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
