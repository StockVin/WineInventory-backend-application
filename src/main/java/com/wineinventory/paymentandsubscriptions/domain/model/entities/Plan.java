package com.wineinventory.paymentandsubscriptions.domain.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "plans")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String planId;

    @Column(nullable = true)
    private String planType;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String paymentFrequency;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private Integer maxProducts;

    protected Plan() {}

    public Plan(String planId, String planType, String description, String paymentFrequency,
                Double price, String currency, Integer maxProducts) {
        this.planId = planId;
        this.planType = planType;
        this.description = description;
        this.paymentFrequency = paymentFrequency;
        this.price = price;
        this.currency = currency;
        this.maxProducts = maxProducts;
    }

    public Long getId() { return id; }
    public String getPlanId() { return planId; }
    public String getPlanType() { return planType != null ? planType : "Free"; }
    public String getDescription() { return description; }
    public String getPaymentFrequency() { return paymentFrequency; }
    public Double getPrice() { return price; }
    public String getCurrency() { return currency; }
    public Integer getMaxProducts() { return maxProducts; }

    public void update(String planId, String planType, String description, String paymentFrequency,
                       Double price, String currency, Integer maxProducts) {
        this.planId = planId;
        this.planType = planType;
        this.description = description;
        this.paymentFrequency = paymentFrequency;
        this.price = price;
        this.currency = currency;
        this.maxProducts = maxProducts;
    }
}
