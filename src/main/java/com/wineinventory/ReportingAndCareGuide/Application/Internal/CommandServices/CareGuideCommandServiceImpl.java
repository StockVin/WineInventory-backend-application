package com.wineinventory.ReportingAndCareGuide.Application.Internal.CommandServices;

import com.wineinventory.InventoryManagement.Domain.Model.Aggregates.Product;
import com.wineinventory.InventoryManagement.Domain.Model.Aggregates.Warehouse;
import com.wineinventory.InventoryManagement.Infrastructure.Persistence.JPA.Repositories.ProductRepository;
import com.wineinventory.InventoryManagement.Infrastructure.Persistence.JPA.Repositories.WarehouseRepository;
import com.wineinventory.ReportingAndCareGuide.Domain.Model.Commands.CreateCareGuideCommand;
import com.wineinventory.ReportingAndCareGuide.Domain.Model.Commands.CreateCareGuideWithoutProductCommand;
import com.wineinventory.ReportingAndCareGuide.Domain.Model.Commands.DeleteCareGuideCommand;
import com.wineinventory.ReportingAndCareGuide.Domain.Model.Commands.UpdateCareGuideCommand;
import com.wineinventory.ReportingAndCareGuide.Domain.Model.Entities.CareGuide;
import com.wineinventory.ReportingAndCareGuide.Domain.Services.CareGuideCommandService;
import com.wineinventory.ReportingAndCareGuide.Infrastructure.Persistence.JPA.Repositories.CareGuideRepository;
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