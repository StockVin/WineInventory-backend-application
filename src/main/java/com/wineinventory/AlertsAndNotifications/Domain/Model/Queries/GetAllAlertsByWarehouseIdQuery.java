package com.wineinventory.AlertsAndNotifications.Domain.Model.Queries;

import com.wineinventory.AlertsAndNotifications.Domain.Model.ValueObjects.WarehouseId;

/**
 * This record defines a query to retrieve all alerts associated with a specific warehouse ID.
 *
 * @summary
 * Query object that encapsulates the information needed to retrieve
 * all alerts associated with a specific warehouse.
 *
 * @param warehouseId The unique identifier of the warehouse for which alerts are being requested.
 * @since 1.0
 */
public record GetAllAlertsByWarehouseIdQuery(WarehouseId warehouseId) {

    /**
     * Validates the query parameters.
     */
    public GetAllAlertsByWarehouseIdQuery {
        if (warehouseId == null) {
            throw new IllegalArgumentException("WarehouseId cannot be null.");
        }
    }
}