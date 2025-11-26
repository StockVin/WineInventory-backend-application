package com.wineinventory.planmanagement.domain.model.aggregates;

import jakarta.persistence.*;

@Entity
@Table(name = "plans")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code; // ej: FREE, PLUS, PRO

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String currency; // "USD", "PEN", etc.

    // ID del plan en PayPal para suscripciones (solo Plus / Pro)
    @Column
    private String paypalPlanId;

    protected Plan() {}

    public Plan(String code, String name, String description,
                Double price, String currency, String paypalPlanId) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.paypalPlanId = paypalPlanId;
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Double getPrice() { return price; }
    public String getCurrency() { return currency; }
    public String getPaypalPlanId() { return paypalPlanId; }

    public void update(String name, String description, Double price,
                       String currency, String paypalPlanId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.paypalPlanId = paypalPlanId;
    }
}
