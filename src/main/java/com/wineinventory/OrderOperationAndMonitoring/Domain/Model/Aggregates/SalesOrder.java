package com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Aggregates;

import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Entities.SalesOrderItem;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Events.OrderCompletedEvent;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.ValueObjects.DeliveryInformation;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.ValueObjects.OrderStatus;
import com.wineinventory.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.wineinventory.shared.domain.model.valueobjects.Money;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Agregado raíz que representa una orden de venta completa dentro del dominio.
 * Contiene la información del comprador, los ítems solicitados y los datos de entrega,
 * además de la lógica necesaria para mantener la coherencia de la orden.
 */
@Entity
@Table(name = "sales_orders")
public class SalesOrder extends AuditableAbstractAggregateRoot<SalesOrder> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column
    private Long buyerId;

    @Column(name = "customer_email", nullable = false)
    private String customerEmail;

    @Column(nullable = false, unique = true)
    private String orderNumber;

    @Column(nullable = false)
    private LocalDateTime orderedAt;

    @Column(name = "delivery_date", nullable = false)
    private LocalDateTime deliveryDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Embedded
    private DeliveryInformation deliveryInformation;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "subtotal_amount", nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "subtotal_currency", nullable = false))
    })
    private Money subtotalAmount;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "tax_amount", nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "tax_currency", nullable = false))
    })
    private Money taxAmount;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "total_amount", nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "total_currency", nullable = false))
    })
    private Money totalAmount;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @OneToMany(mappedBy = "salesOrder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<SalesOrderItem> items = new ArrayList<>();

    protected SalesOrder() {
        // Required by JPA
    }


    private SalesOrder(Long buyerId, String customerEmail, String currency, DeliveryInformation deliveryInformation,
                       String notes, LocalDateTime deliveryDate, OrderStatus initialStatus) {
        this.buyerId = buyerId;
        this.customerEmail = Objects.requireNonNull(customerEmail, "The customer email is required");
        this.deliveryInformation = Objects.requireNonNull(deliveryInformation, "Delivery information is required");
        this.notes = notes;
        this.status = Objects.requireNonNullElse(initialStatus, OrderStatus.PENDING);
        this.orderNumber = generateOrderNumber();
        this.orderedAt = LocalDateTime.now();
        this.deliveryDate = Objects.requireNonNull(deliveryDate, "Delivery date is required");
        String resolvedCurrency = Objects.requireNonNull(currency, "Currency is required");
        this.subtotalAmount = Money.of(0.0, resolvedCurrency);
        this.taxAmount = Money.of(0.0, resolvedCurrency);
        this.totalAmount = Money.of(0.0, resolvedCurrency);
    }


    public static SalesOrder create(Long buyerId, String customerEmail, String currency,
                                    DeliveryInformation deliveryInformation, String notes,
                                    LocalDateTime deliveryDate, OrderStatus initialStatus) {
        return new SalesOrder(buyerId, customerEmail, currency, deliveryInformation, notes, deliveryDate, initialStatus);
    }


    private String generateOrderNumber() {
        return "SO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ROOT);
    }


    public void addItem(SalesOrderItem item) {
        Objects.requireNonNull(item, "The item to add cannot be null");
        if (!item.getUnitPrice().currency().equalsIgnoreCase(this.totalAmount.currency())) {
            throw new IllegalArgumentException("All order items must share the same currency");
        }
        item.assignOrder(this);
        this.items.add(item);
        recalculateTotals();
    }


    public void updateStatus(OrderStatus newStatus) {
        Objects.requireNonNull(newStatus, "Order status is required");
        this.status = newStatus;
        if (OrderStatus.COMPLETED.equals(newStatus)) {
            this.addDomainEvent(new OrderCompletedEvent(this.id, this.orderNumber, LocalDateTime.now()));
        }
    }


    private void recalculateTotals() {
        double subtotal = this.items.stream()
                .mapToDouble(item -> item.getLineTotal().amount())
                .sum();
        this.subtotalAmount = Money.of(subtotal, this.totalAmount.currency());
        this.totalAmount = Money.of(subtotal + this.taxAmount.amount(), this.totalAmount.currency());
    }


    public Long getId() {
        return id;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public LocalDateTime getOrderedAt() {
        return orderedAt;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public DeliveryInformation getDeliveryInformation() {
        return deliveryInformation;
    }

    public Money getTotalAmount() {
        return totalAmount;
    }

    public Money getSubtotalAmount() {
        return subtotalAmount;
    }

    public Money getTaxAmount() {
        return taxAmount;
    }

    public String getNotes() {
        return notes;
    }

    public List<SalesOrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public String getCurrency() {
        return totalAmount.currency();
    }

    public void updateTaxAmount(Double taxAmount) {
        double resolvedTax = taxAmount == null ? 0.0 : taxAmount;
        if (resolvedTax < 0) {
            throw new IllegalArgumentException("The tax amount cannot be negative");
        }
        this.taxAmount = Money.of(resolvedTax, this.totalAmount.currency());
        this.totalAmount = Money.of(this.subtotalAmount.amount() + this.taxAmount.amount(), this.totalAmount.currency());
    }
}
