package com.wineinventory.paymentandsubscriptions.domain.model.aggregates;

import java.time.LocalDateTime;

public class SubscriptionStates {

    private LocalDateTime expirationDate;
    
    private String preferenceId;
    
    private String pendingPlanId;
    
    private LocalDateTime updatedAt;

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(String preferenceId) {
        this.preferenceId = preferenceId;
    }

    public String getPendingPlanId() {
        return pendingPlanId;
    }

    public void setPendingPlanId(String pendingPlanId) {
        this.pendingPlanId = pendingPlanId;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public void markAsPending(String planId, String preferenceId) {
        this.preferenceId = preferenceId;
        this.expirationDate = LocalDateTime.now();
    }

    public void markAsPendingUpgrade(String newPlanId, String preferenceId) {
        this.pendingPlanId = newPlanId;
        this.preferenceId = preferenceId;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void cancelSubscription() {
        this.expirationDate = LocalDateTime.now();
    }
    
    public void markAsExpired() {
    }
    

    public boolean isPendingPayment() {
        return false; 
    }
    
    public void markAsCancelled() {
    }

    public void markAsCancelledUpdate() {
        this.pendingPlanId = null;
        this.preferenceId = null;
    }
}
