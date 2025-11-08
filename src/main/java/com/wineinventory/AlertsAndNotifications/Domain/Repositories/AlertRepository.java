package com.wineinventory.AlertsAndNotifications.Domain.Repositories;

import com.wineinventory.AlertsAndNotifications.Domain.Model.Aggregates.Alert;
import com.wineinventory.AlertsAndNotifications.Domain.Model.ValueObjects.AccountId;
import com.wineinventory.AlertsAndNotifications.Domain.Model.ValueObjects.ProductId;
import com.wineinventory.AlertsAndNotifications.Domain.Model.ValueObjects.WarehouseId;

import java.util.List;

/**
 * This interface defines the contract for a repository that manages Alert aggregates.
 *
 * @summary
 * Repository interface for Alert aggregates that provides methods for
 * persisting and retrieving alert data from the database.
 *
 * @since 1.0
 */
public interface AlertRepository {

    /**
     * This method retrieves all alerts associated with a specific product ID.
     *
     * @param productId The product ID to search for.
     * @return A list of alerts associated with the specified product ID.
     */
    List<Alert> findByProductId(ProductId productId);

    /**
     * This method retrieves all alerts associated with a specific account ID.
     *
     * @param accountId The account ID to search for.
     * @return A list of alerts associated with the specified account ID.
     */
    List<Alert> findByAccountId(AccountId accountId);

    /**
     * This method retrieves all alerts associated with a specific warehouse ID.
     *
     * @param warehouseId The warehouse ID to search for.
     * @return A list of alerts associated with the specified warehouse ID.
     */
    List<Alert> findByWarehouseId(WarehouseId warehouseId);
}