package com.wineinventory.inventorymanagement.application.internal.commandservices;

import com.wineinventory.inventorymanagement.domain.model.aggregates.Inventory;
import com.wineinventory.inventorymanagement.domain.model.commands.*;
import com.wineinventory.inventorymanagement.domain.model.valueobjects.ProductBestBeforeDate;
import com.wineinventory.inventorymanagement.domain.model.valueobjects.ProductStock;
import com.wineinventory.inventorymanagement.domain.services.InventoryCommandService;
import com.wineinventory.inventorymanagement.infrastructure.persistence.jpa.repositories.InventoryRepository;
import com.wineinventory.inventorymanagement.infrastructure.persistence.jpa.repositories.ProductRepository;
import com.wineinventory.inventorymanagement.infrastructure.persistence.jpa.repositories.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * InventoryCommandServiceImpl
 *
 * @summary
 * InventoryCommandServiceImpl is an implementation of the InventoryCommandService interface.
 *
 * @since 1.0.0
 */
@Service
public class InventoryCommandServiceImpl implements InventoryCommandService {

    /**
     * Repository for accessing product data.
     */
    private final ProductRepository productRepository;

    /**
     * Repository for accessing inventory data.
     */
    private final InventoryRepository inventoryRepository;

    /**
     * Repository for accessing warehouse data.
     */
    private final WarehouseRepository warehouseRepository;

    public InventoryCommandServiceImpl(ProductRepository productRepository, InventoryRepository inventoryRepository, WarehouseRepository warehouseRepository) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.warehouseRepository = warehouseRepository;
    }

    /**
     * Handles the command for adding stock to a product in a warehouse.
     *
     * @param command The command containing the details for adding stock to a product.
     * @return The updated inventory object.
     */
    @Override
    public Optional<Inventory> handle(AddStockToProductCommand command) {

        var product = productRepository.findById(command.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product with ID %s does not exist".formatted(command.productId())));

        var warehouse = warehouseRepository.findById(command.warehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Warehouse with ID %s does not exist".formatted(command.warehouseId())));

        var inventoryToUpdate = inventoryRepository.findByProductAndWarehouse(product, warehouse);

        try {
            Inventory inventory = inventoryToUpdate.get();
            inventory.addStockToProduct(command.addedQuantity());
            inventory.updatedBestBeforeDate(command.bestBeforeDate());
            var inventoryUpdated = inventoryRepository.save(inventory);
            return Optional.of(inventoryUpdated);
        } catch (Exception e) {
            throw new RuntimeException("Error updating inventory: " + e.getMessage(), e);
        }
    }

    /**
     * This method handles the command for reducing stock of a product in a specific warehouse.
     * In other words, updating an inventory object.
     *
     * @param command The command containing the details for reducing stock from a product.
     * @return The updated inventory object.
     */
    @Override
    public Optional<Inventory> handle(ReduceStockFromProductCommand command) {
        var product = productRepository.findById(command.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product with ID %s does not exist".formatted(command.productId())));

        var warehouse = warehouseRepository.findById(command.warehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Warehouse with ID %s does not exist".formatted(command.warehouseId())));

        var targetBestBeforeDate = new ProductBestBeforeDate(command.bestBeforeDate());

        var inventoryToUpdate = inventoryRepository.findByProductAndWarehouse(product, warehouse)
                .orElseThrow(() -> new IllegalArgumentException("Inventory does not exists."));

        try {
            inventoryToUpdate.reduceStockFromProduct(command.removedQuantity());
            inventoryToUpdate.updatedBestBeforeDate(command.bestBeforeDate());
            var inventoryUpdated = inventoryRepository.save(inventoryToUpdate);
            return Optional.of(inventoryUpdated);
        } catch (Exception e) {
            throw new RuntimeException("Error updating inventory: " + e.getMessage(), e);
        }
    }

    /**
     * This method handles the command for creating an inventory object that links a specific product and a specific warehouse.
     *
     * @param command The command containing the details for creating a new inventory entry.
     * @return The created inventory object if created, null if not.
     */
    @Override
    public Optional<Inventory> handle(AddProductsToWarehouseCommand command) {

        var product = productRepository.findById(command.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product with ID %s does not exist".formatted(command.productId())));

        var warehouse = warehouseRepository.findById(command.warehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Warehouse with ID %s does not exist".formatted(command.warehouseId())));

        if (inventoryRepository.existsByProduct_ProductIdAndWarehouse_WarehouseId(command.productId(), command.warehouseId())) {
            throw new IllegalArgumentException("Product with ID %s already exists in warehouse with ID %s.".formatted(command.productId(), command.warehouseId()));
        }

        var inventory = new Inventory(product, warehouse, command.quantity(), command.bestBeforeDate());

        try {
            var inventoryCreated = inventoryRepository.save(inventory);
            return Optional.of(inventoryCreated);
        } catch (Exception e) {
            throw new RuntimeException("Error creating inventory: " + e.getMessage(), e);
        }
    }

    /**
     * This method handles the command for moving stock from a current warehouse to another warehouse.
     *
     * @param command The command containing the details for moving stock.
     * @return The ID of the product whose stock is being moved to another warehouse.
     */
    @Override
    public Optional<Inventory> handle(MoveProductToAnotherWarehouseCommand command) {

        var productToMove = productRepository.findById(command.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product with ID %s does not exist".formatted(command.productId())));

        var newWarehouse = warehouseRepository.findById(command.newWarehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Warehouse with ID %s does not exist".formatted(command.productId())));

        var oldWarehouse = warehouseRepository.findById(command.oldWarehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Warehouse with ID %s does not exist".formatted(command.productId())));

        if (command.newWarehouseId().equals(command.oldWarehouseId())) {
            throw new IllegalArgumentException("Cannot move products to the same warehouse.");
        }

        var targetBestBeforeDate = new ProductBestBeforeDate(command.bestBeforeDate());

        var currentInventory = inventoryRepository.findByProductAndWarehouseAndProductBestBeforeDate(
                productToMove,
                oldWarehouse,
                targetBestBeforeDate
        ).orElseThrow(() -> new IllegalArgumentException("Inventory does not exists."));

        currentInventory.reduceStockFromProduct(command.quantityToMove());

        inventoryRepository.save(currentInventory);

        var newInventory = inventoryRepository.findByProductAndWarehouseAndProductBestBeforeDate(
                productToMove,
                newWarehouse,
                targetBestBeforeDate
        );

        if (newInventory.isEmpty()) {
            try {
                var targetAddedQuantity = new ProductStock(command.quantityToMove());
                var newMovedInventory = new Inventory(productToMove, newWarehouse, command.quantityToMove(), command.bestBeforeDate());

                productToMove.addWarehouseRelation(newMovedInventory);

                productRepository.save(productToMove);
                inventoryRepository.save(newMovedInventory);
                return Optional.of(newMovedInventory);
            } catch (Exception e) {
                throw new RuntimeException("Error moving products: " + e.getMessage(), e);
            }
        }

        else {
            try {
                Inventory inventory = newInventory.get();
                inventory.addStockToProduct(command.quantityToMove());
                inventoryRepository.save(inventory);
                return Optional.of(inventory);
            } catch (Exception e) {
                throw new RuntimeException("Error moving products: " + e.getMessage(), e);
            }
        }
    }

    /**
     * This method handles the command to delete an inventory.
     *
     * @param command The command containing the details for deleting an inventory.
     * @return The ID of the product whose inventory is being deleted.
     */
    @Override
    public Long handle(DeleteProductFromWarehouseCommand command) {

        var product = productRepository.findById(command.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product with ID %s does not exist".formatted(command.productId())));

        var warehouse = warehouseRepository.findById(command.warehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Warehouse with ID %s does not exist".formatted(command.warehouseId())));

        var targetBestBeforeDate = new ProductBestBeforeDate(command.bestBeforeDate());

        var inventoryToDelete = inventoryRepository.findByProductAndWarehouseAndProductBestBeforeDate(product, warehouse, targetBestBeforeDate)
                .orElseThrow(() -> new IllegalArgumentException("Inventory does not exists."));

        product.removeWarehouseRelation(inventoryToDelete);
        productRepository.save(product);

        if (inventoryToDelete.getProductStock().stock() == 0) {
            try {
                inventoryRepository.delete(inventoryToDelete);
                return product.getProductId();
            } catch (Exception e) {
                throw new RuntimeException("Error updating product: " + e.getMessage(), e);
            }
        } else {
            throw  new IllegalArgumentException("Cannot delete an inventory if it has stock remaining.");
        }
    }
}