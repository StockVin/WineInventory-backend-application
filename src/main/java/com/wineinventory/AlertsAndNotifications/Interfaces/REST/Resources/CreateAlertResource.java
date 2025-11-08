package com.wineinventory.AlertsAndNotifications.Interfaces.REST.Resources;

import com.wineinventory.AlertsAndNotifications.Domain.Model.ValueObjects.AccountId;
import com.wineinventory.AlertsAndNotifications.Domain.Model.ValueObjects.ProductId;
import com.wineinventory.AlertsAndNotifications.Domain.Model.ValueObjects.WarehouseId;

/**
 * This record defines the resource for creating a new alert.
 *
 * @summary
 * Resource object that represents the data needed to create a new alert,
 * containing all the required information for alert creation.
 *
 * @param title      The title of the alert.
 * @param message    The message content of the alert.
 * @param severity   The severity level of the alert.
 * @param type       The type of the alert.
 * @param accountId  The unique identifier of the account associated with the alert.
 * @param productId  The unique identifier of the product associated with the alert.
 * @param warehouseId The unique identifier of the warehouse associated with the alert.
 * @since 1.0
 */
public record CreateAlertResource(
        String title,
        String message,
        String severity,
        String type,
        AccountId accountId,
        ProductId productId,
        WarehouseId warehouseId) {
}