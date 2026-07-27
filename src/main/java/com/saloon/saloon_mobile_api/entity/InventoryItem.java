package com.saloon.saloon_mobile_api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Product Name
     * Example:
     * Shampoo
     * Conditioner
     * Hair Color
     * Bleach
     */
    @Column(nullable = false, unique = true, length = 100)
    private String name;

    /**
     * Inventory Category
     * HAIR_CARE
     * SKIN_CARE
     * CHEMICAL
     * TOOL
     * ACCESSORY
     * CONSUMABLE
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InventoryCategory category;

    /**
     * Optional Brand
     * Loreal
     * Matrix
     * Wella
     */
    @Column(length = 100)
    private String brand;

    /**
     * Current Available Stock
     */
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal availableQuantity = BigDecimal.ZERO;

    /**
     * Inventory Unit
     * ML
     * GM
     * PCS
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Unit unit;

    /**
     * Alert Quantity
     */
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal minimumQuantity = BigDecimal.ZERO;

    /**
     * Average Purchase Price
     */
    @Column(precision = 12, scale = 2)
    private BigDecimal purchasePrice;

    /**
     * Selling Price (optional)
     */
    @Column(precision = 12, scale = 2)
    private BigDecimal sellingPrice;

    /**
     * Supplier Name
     * (No Supplier table)
     */
    @Column(length = 100)
    private String supplierName;

    /**
     * Remarks
     */
    @Column(length = 500)
    private String remarks;

    @Column(nullable = false)
    private Boolean active = true;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}