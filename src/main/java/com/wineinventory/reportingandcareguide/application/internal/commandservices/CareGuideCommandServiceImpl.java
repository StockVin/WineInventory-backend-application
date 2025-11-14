package com.wineinventory.reportingandcareguide.application.internal.commandservices;

import com.wineinventory.inventorymanagement.domain.model.aggregates.Product;
import com.wineinventory.inventorymanagement.domain.model.aggregates.Warehouse;
import com.wineinventory.inventorymanagement.infrastructure.persistence.jpa.repositories.ProductRepository;
import com.wineinventory.inventorymanagement.infrastructure.persistence.jpa.repositories.WarehouseRepository;
import com.wineinventory.reportingandcareguide.domain.model.commands.CreateCareGuideCommand;
import com.wineinventory.reportingandcareguide.domain.model.commands.CreateCareGuideWithoutProductCommand;
import com.wineinventory.reportingandcareguide.domain.model.commands.DeleteCareGuideCommand;
import com.wineinventory.reportingandcareguide.domain.model.commands.UpdateCareGuideCommand;
import com.wineinventory.reportingandcareguide.domain.model.entities.CareGuide;
import com.wineinventory.reportingandcareguide.domain.services.CareGuideCommandService;
import com.wineinventory.reportingandcareguide.infrastructure.persistence.jpa.repositories.CareGuideRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Implementation of the CareGuideCommandService interface.
 * Handles the creation, update, and deletion of care guides.
 */
@Service
public class CareGuideCommandServiceImpl implements CareGuideCommandService {

    private final CareGuideRepository careGuideRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    public CareGuideCommandServiceImpl(
            CareGuideRepository careGuideRepository,
            ProductRepository productRepository,
            WarehouseRepository warehouseRepository) {
        this.careGuideRepository = careGuideRepository;
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    @Transactional
    public Optional<CareGuide> handle(CreateCareGuideCommand command) {
        try {
            Product product = productRepository.findAll().stream().findFirst().orElse(null);
            Warehouse warehouse = warehouseRepository.findAll().stream().findFirst().orElse(null);

            CareGuide careGuide = new CareGuide(
                    command.accountId(),
                    product,
                    warehouse,
                    command.guideName(),
                    command.type(),
                    command.description(),
                    command.imageUrl()
            );

            return Optional.of(careGuideRepository.save(careGuide));

        } catch (Exception e) {
            throw new IllegalStateException("Failed to create care guide: " + e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public Optional<CareGuide> handle(UpdateCareGuideCommand command) {
        return careGuideRepository.findById(command.id())
                .map(careGuide -> {
                    careGuide.updateInformation(
                            command.guideName(),
                            command.type(),
                            command.description()
                    );
                    // Update the image URL if provided
                    if (command.imageUrl() != null && !command.imageUrl().isBlank()) {
                        return null;
                    }
                    return careGuideRepository.save(careGuide);
                });
    }

    @Override
    @Transactional
    public void handle(DeleteCareGuideCommand command) {
        careGuideRepository.findById(command.id())
                .ifPresentOrElse(
                        careGuide -> careGuideRepository.delete(careGuide),
                        () -> { throw new NoSuchElementException("Care guide not found with id: " + command.id()); }
                );
    }
    @Override
    @Transactional
    public Optional<CareGuide> handle(CreateCareGuideWithoutProductCommand command) {
        try {
            CareGuide careGuide = new CareGuide(
                    command.accountId(),
                    null,
                    null,
                    command.guideName(),
                    command.type(),
                    command.description(),
                    command.imageUrl()
            );
            return Optional.of(careGuideRepository.save(careGuide));
        } catch (Exception e) {
            throw new IllegalStateException("Failed to create care guide: " + e.getMessage(), e);
        }
    }
}