package com.wineinventory.paymentandsubscriptions.domain.model.aggregates;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long planId;

    @Column(nullable = true)
    private String paypalSubscriptionId;

    @Column(nullable = false)
    private String status; 

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private Double amount;

    private LocalDateTime startDate;
    private LocalDateTime nextBillingDate;
    
    @Column(name = "approval_url")
    private String approvalUrl;

    protected Subscription() {}

    public Subscription(
            Long userId,
            Long planId,
            String paypalSubscriptionId,
            String status,
            String currency,
            Double amount,
            LocalDateTime startDate,
            LocalDateTime nextBillingDate,
            String approvalUrl) {

        this.userId = userId;
        this.planId = planId;
        this.paypalSubscriptionId = paypalSubscriptionId;
        this.status = status;
        this.currency = currency;
        this.amount = amount;
        this.startDate = startDate;
        this.nextBillingDate = nextBillingDate;
        this.approvalUrl = approvalUrl;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getPlanId() {
        return planId;
    }

    public String getPaypalSubscriptionId() {
        return paypalSubscriptionId;
    }

    public String getStatus() {
        return status;
    }

    public String getCurrency() {
        return currency;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getNextBillingDate() {
        return nextBillingDate;
    }
    
    public String getApprovalUrl() {
        return approvalUrl;
    }

    public void activate() {
        this.status = "ACTIVE";
    }

    public void cancel() {
        this.status = "CANCELLED";
    }
    public void setNextBillingDate(LocalDateTime nextBillingDate) {
        this.nextBillingDate = nextBillingDate;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setPaypalSubscriptionId(String paypalSubscriptionId) {
        this.paypalSubscriptionId = paypalSubscriptionId;
    }
    public void setApprovalUrl(String approvalUrl) {
        this.approvalUrl = approvalUrl;
    }


}
