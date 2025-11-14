package com.wineinventory.orderoperationandmonitoring.application.internal.commandservices;

import com.wineinventory.orderoperationandmonitoring.domain.model.aggregates.SalesOrder;
import com.wineinventory.orderoperationandmonitoring.domain.model.commands.GenerateSalesOrderCommand;
import com.wineinventory.orderoperationandmonitoring.domain.model.entities.SalesOrderItem;
import com.wineinventory.orderoperationandmonitoring.domain.model.valueobjects.OrderStatus;
import com.wineinventory.orderoperationandmonitoring.domain.repositories.SalesOrderRepository;
import com.wineinventory.orderoperationandmonitoring.domain.services.SalesOrderCommandService;
import com.wineinventory.shared.domain.model.valueobjects.Money;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Implementación concreta de las operaciones de escritura sobre órdenes de venta.
 * Orquesta la lógica de negocio combinando el agregado y el repositorio subyacente.
 */
@Service
@Transactional
public class SalesOrderCommandServiceImpl implements SalesOrderCommandService {

    private final SalesOrderRepository salesOrderRepository;

    public SalesOrderCommandServiceImpl(SalesOrderRepository salesOrderRepository) {
        this.salesOrderRepository = salesOrderRepository;
    }

    @Override
    public SalesOrder handle(GenerateSalesOrderCommand command) {
        // Validaciones defensivas para asegurar que el comando esté completo antes de crear la orden.
        Objects.requireNonNull(command, "The command to generate a sales order is required");
        if (command.items() == null || command.items().isEmpty()) {
            throw new IllegalArgumentException("A sales order requires at least one item");
        }
        if (command.currency() == null || command.currency().isBlank()) {
            throw new IllegalArgumentException("A valid currency code is required for the sales order");
        }
        if (command.customerEmail() == null || command.customerEmail().isBlank()) {
            throw new IllegalArgumentException("A customer email is required for the sales order");
        }
        if (command.deliveryDate() == null) {
            throw new IllegalArgumentException("A delivery date is required for the sales order");
        }
        if (command.deliveryInformation() == null) {
            throw new IllegalArgumentException("Delivery information is required for the sales order");
        }

        SalesOrder salesOrder = SalesOrder.create(command.buyerId(), command.customerEmail(), command.currency(),
                command.deliveryInformation(), command.notes(), command.deliveryDate(), command.initialStatus());

        command.items().forEach(item -> {
            validateItem(item);
            Money unitPrice = Money.of(item.unitPrice(), command.currency());
            SalesOrderItem salesOrderItem = new SalesOrderItem(item.productId(), item.productName(), item.quantity(), unitPrice);
            salesOrder.addItem(salesOrderItem);
        });
        salesOrder.updateTaxAmount(command.taxAmount());

        return salesOrderRepository.save(salesOrder);
    }

    @Override
    public SalesOrder updateStatus(Long orderId, OrderStatus newStatus) {

        SalesOrder salesOrder = salesOrderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Sales order with id " + orderId + " was not found"));

        salesOrder.updateStatus(newStatus);
        return salesOrderRepository.save(salesOrder);
    }

    @Override
    public void delete(Long orderId) {

        if (!salesOrderRepository.existsById(orderId)) {
            throw new EntityNotFoundException("Sales order with id " + orderId + " was not found");
        }
        salesOrderRepository.deleteById(orderId);
    }

    private void validateItem(GenerateSalesOrderCommand.Item item) {

        Objects.requireNonNull(item.productId(), "The item product identifier is required");
        Objects.requireNonNull(item.productName(), "The item product name is required");
        Objects.requireNonNull(item.quantity(), "The item quantity is required");
        Objects.requireNonNull(item.unitPrice(), "The item unit price is required");
        if (item.quantity() <= 0) {
            throw new IllegalArgumentException("The item quantity must be greater than zero");
        }
        if (item.unitPrice() <= 0) {
            throw new IllegalArgumentException("The item unit price must be greater than zero");
        }
    }
}
