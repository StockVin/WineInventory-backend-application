package com.wineinventory.AlertsAndNotifications.Application.Internal.QueryServices;

import com.wineinventory.AlertsAndNotifications.Domain.Model.Aggregates.Alert;
import com.wineinventory.AlertsAndNotifications.Domain.Model.Queries.GetAlertByIdQuery;
import com.wineinventory.AlertsAndNotifications.Domain.Model.Queries.GetAllAlertsByAccountIdQuery;
import com.wineinventory.AlertsAndNotifications.Domain.Model.Queries.GetAllAlertsByProductIdQuery;
import com.wineinventory.AlertsAndNotifications.Domain.Model.Queries.GetAllAlertsByWarehouseIdQuery;
import com.wineinventory.AlertsAndNotifications.Domain.Services.AlertQueryService;
import com.wineinventory.AlertsAndNotifications.Infrastructure.Persistence.JPA.Repositories.AlertJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This class implements the IAlertQueryService interface to handle queries related to alerts.
 *
 * @summary
 * Implementation of the AlertQueryService interface that handles all query operations
 * related to alerts, including retrieval by various criteria.
 *
 * @since 1.0
 */
@Service
public class AlertQueryServiceImpl implements AlertQueryService {

    private final AlertJpaRepository alertRepository;

    /**
     * Constructor for AlertQueryServiceImpl.
     *
     * @param alertRepository The repository for accessing alert data.
     */
    public AlertQueryServiceImpl(AlertJpaRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    /**
     * This method retrieves an alert by its ID.
     *
     * @param query The query containing the alert ID.
     * @return The alert with the specified ID, or empty if not found.
     */
    @Override
    public Optional<Alert> handle(GetAlertByIdQuery query) {
        var alertId = Long.parseLong(query.alertId());
        return alertRepository.findById(alertId);
    }

    /**
     * This method retrieves all alerts for a specific product ID.
     *
     * @param query The query containing the product ID.
     * @return A list of alerts associated with the specified product ID.
     */
    @Override
    public List<Alert> handle(GetAllAlertsByProductIdQuery query) {
        return alertRepository.findByProductId(query.productId());
    }

    /**
     * This method retrieves all alerts for a specific profile ID.
     *
     * @param query The query containing the profile ID.
     * @return A list of alerts associated with the specified profile ID.
     */
    @Override
    public List<Alert> handle(GetAllAlertsByAccountIdQuery query) {
        return alertRepository.findByAccountId(query.accountId());
    }

    /**
     * This method retrieves all alerts for a specific warehouse ID.
     *
     * @param query The query containing the warehouse ID.
     * @return A list of alerts associated with the specified warehouse ID.
     */
    @Override
    public List<Alert> handle(GetAllAlertsByWarehouseIdQuery query) {
        return alertRepository.findByWarehouseId(query.warehouseId());
    }
}