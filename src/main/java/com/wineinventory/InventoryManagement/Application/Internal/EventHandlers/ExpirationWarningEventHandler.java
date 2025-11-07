package com.wineinventory.InventoryManagement.Application.Internal.EventHandlers;

import com.wineinventory.InventoryManagement.Domain.Model.Aggregates.Inventory;
import com.wineinventory.InventoryManagement.Domain.Model.Events.ProductProblemDetectedEvent;
import com.wineinventory.InventoryManagement.Infrastructure.Persistence.JPA.Repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * Event handler for expiration warning checks.
 * This component runs daily to check all inventories for expiration warnings and generates alerts when necessary.
 * It ensures that alerts are generated based on current dates and prevents duplicates.
 *
 * @since 1.0
 */
@Component
public class ExpirationWarningEventHandler {

    private final InventoryRepository inventoryRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Value("${expiration.check.enabled:true}")
    private boolean enabled;

    public ExpirationWarningEventHandler(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    /**
     * Scheduled event handler that runs daily at 8:00 AM to check all inventories for expiration warnings.
     * This ensures that alerts are generated based on the current date and prevents duplicates.
     */
    @Scheduled(cron = "${expiration.check.cron:0 0 8 * * ?}")
    public void handleExpirationWarningCheck() {
        if (!enabled) {
            return;
        }

        List<Inventory> allInventories = inventoryRepository.findAll();

        for (Inventory inventory : allInventories) {
            try {
                ProductProblemDetectedEvent event = inventory.checkExpirationWarning();
                if (event != null) {
                    eventPublisher.publishEvent(event);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}