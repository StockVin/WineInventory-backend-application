package com.wineinventory.AlertsAndNotifications.Domain.Model.Queries;

import com.wineinventory.AlertsAndNotifications.Domain.Model.ValueObjects.AccountId;

/**
 * This query is used to retrieve all alerts associated with a specific account.
 *
 * @summary
 * Query object that encapsulates the information needed to retrieve
 * all alerts associated with a specific account.
 *
 * @param accountId The unique identifier of the account for which alerts are being requested.
 * @since 1.0
 */
public record GetAllAlertsByAccountIdQuery(AccountId accountId) {
    /**
     * Validates the query parameters.
     */
    public GetAllAlertsByAccountIdQuery {
        if (accountId == null) {
            throw new IllegalArgumentException("AccountId cannot be null.");
        }
    }
}