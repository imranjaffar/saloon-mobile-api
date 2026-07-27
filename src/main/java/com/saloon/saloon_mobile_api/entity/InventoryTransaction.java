package com.saloon.saloon_mobile_api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Inventory Item
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_item_id", nullable = false)
    private InventoryItem inventoryItem;

    /**
     * STOCK_IN
     * STOCK_OUT
     * ADJUSTMENT
     * DEDUCT
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionType transactionType;

    /**
     * Quantity affected
     */
    @Column(nullable = false, precision = 19, scale = 3)
    private BigDecimal quantity;

    /**
     * Stock before transaction
     */
    @Column(nullable = false, precision = 19, scale = 3)
    private BigDecimal stockBefore;

    /**
     * Stock after transaction
     */
    @Column(nullable = false, precision = 19, scale = 3)
    private BigDecimal stockAfter;

    /**
     * Purchase cost per unit
     * (Only applicable for STOCK_IN)
     */
    @Column(precision = 19, scale = 2)
    private BigDecimal unitPrice;

    /**
     * Total amount
     * quantity × unitPrice
     */
    @Column(precision = 19, scale = 2)
    private BigDecimal totalAmount;

    /**
     * Supplier name for purchase
     */
    @Column(length = 100)
    private String supplierName;

    /**
     * PURCHASE
     * APPOINTMENT
     * MANUAL
     * SALE
     * ADJUSTMENT
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReferenceType referenceType;

    /**
     * Appointment Id / Sale Id etc.
     */
    private Long referenceId;

    /**
     * Optional remarks
     */
    @Column(length = 500)
    private String remarks;

    /**
     * Transaction date
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}