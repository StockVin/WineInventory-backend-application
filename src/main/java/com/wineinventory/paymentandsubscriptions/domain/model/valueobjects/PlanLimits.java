package com.wineinventory.paymentandsubscriptions.domain.model.valueobjects;


public class PlanLimits {
    
    private final int maxUsers;
    
    private final int maxWarehouses;
    
    private final int maxProducts;

    public PlanLimits(int maxUsers, int maxWarehouses, int maxProducts) {
        if (maxProducts < 0) {
            throw new IllegalArgumentException("Max products cannot be negative");
        }
        if (maxUsers < 0) {
            throw new IllegalArgumentException("Max users cannot be negative");
        }
        if (maxWarehouses < 0) {
            throw new IllegalArgumentException("Max warehouses cannot be negative");
        }
        
        this.maxProducts = maxProducts;
        this.maxUsers = maxUsers;
        this.maxWarehouses = maxWarehouses;
    }

    public static PlanLimits forType(PlanType planType) {
        switch (planType) {
            case Free:
                return new PlanLimits(10, 5, 500);
            case Plus:
                return new PlanLimits(100, 20, 2000);
            case Pro:
                return new PlanLimits(200, 40, 20000);
            default:
                throw new IllegalArgumentException("Unsupported plan type: " + planType);
        }
    }

    public int getMaxUsers() {
        return maxUsers;
    }
    
    public int getMaxWarehouses() {
        return maxWarehouses;
    }
    
    public int getMaxProducts() {
        return maxProducts;
    }

    @Override
    public String toString() {
        return "PlanLimits{" +
                "maxUsers=" + maxUsers +
                ", maxWarehouses=" + maxWarehouses +
                ", maxProducts=" + maxProducts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlanLimits that = (PlanLimits) o;

        if (maxUsers != that.maxUsers) return false;
        if (maxWarehouses != that.maxWarehouses) return false;
        return maxProducts == that.maxProducts;
    }

    @Override
    public int hashCode() {
        int result = maxUsers;
        result = 31 * result + maxWarehouses;
        result = 31 * result + maxProducts;
        return result;
    }
}
