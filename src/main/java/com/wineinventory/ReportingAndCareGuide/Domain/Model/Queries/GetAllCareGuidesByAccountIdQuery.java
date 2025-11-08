package com.wineinventory.ReportingAndCareGuide.Domain.Model.Queries;

public record GetAllCareGuidesByAccountIdQuery(String accountId) {
    public GetAllCareGuidesByAccountIdQuery {
        if (accountId == null || accountId.isBlank()) {
            throw new IllegalArgumentException("Account ID cannot be null or empty");
        }
    }
}