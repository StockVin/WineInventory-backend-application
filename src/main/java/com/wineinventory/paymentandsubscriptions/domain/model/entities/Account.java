package com.wineinventory.paymentandsubscriptions.domain.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String accountId;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String businessId;

    protected Account() {}

    public Account(String accountId, String role, String businessId) {
        this.accountId = accountId;
        this.role = role;
        this.businessId = businessId;
    }

    public Long getId() { return id; }
    public String getAccountId() { return accountId; }
    public String getRole() { return role; }
    public String getBusinessId() { return businessId; }

    public void setBusinessId(String businessId) { this.businessId = businessId; }
}
