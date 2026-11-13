package com.saloon.saloon_mobile_api.dto;

import com.saloon.saloon_mobile_api.entity.InventoryCategory;
import com.saloon.saloon_mobile_api.entity.Unit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {

    private Long id;

    /**
     * Product Name
     */
    private String name;

    /**
     * Category
     */
    private InventoryCategory category;

    /**
     * Brand
     */
    private String brand;

    /**
     * Current Available Quantity
     */
    private BigDecimal availableQuantity;

    /**
     * Inventory Unit
     */
    private Unit unit;

    /**
     * Minimum Quantity before Low Stock Alert
     */
    private BigDecimal minimumQuantity;

    /**
     * Average Purchase Price
     */
    private BigDecimal purchasePrice;

    /**
     * Selling Price (Optional)
     */
    private BigDecimal sellingPrice;

    /**
     * Supplier Name (Stored as text)
     */
    private String supplierName;

    /**
     * Remarks
     */
    private String remarks;

    /**
     * Active / Inactive
     */
    private Boolean active;

    /**
     * Computed field
     */
    private Boolean lowStock;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String packageName;
    private BigDecimal unitsPerPackage;

}