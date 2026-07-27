package com.saloon.saloon_mobile_api.dto;

import com.saloon.saloon_mobile_api.entity.ReferenceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockInRequest {

    /**
     * Inventory Item
     */
    @NotNull(message = "Inventory item is required.")
    private Long inventoryId;

    /**
     * Quantity entered by user.
     *
     * Examples:
     * PACKAGE mode -> 5 Bottles
     * UNIT mode -> 500 ml
     */
    @NotNull(message = "Quantity is required.")
    @Positive(message = "Quantity must be greater than zero.")
    private BigDecimal quantity;

    /**
     * UNIT / PACKAGE
     */
    @NotNull(message = "Quantity mode is required.")
    private QuantityMode quantityMode;

    /**
     * Purchase price per package or per unit
     * depending on QuantityMode.
     */
    @NotNull(message = "Unit price is required.")
    @Positive(message = "Unit price must be greater than zero.")
    private BigDecimal unitPrice;

    /**
     * Supplier name
     */
    @NotBlank(message = "Supplier name is required.")
    private String supplierName;

    /**
     * PURCHASE / RETURN / MANUAL
     */
    @NotNull(message = "Reference type is required.")
    private ReferenceType referenceType;

    /**
     * Optional invoice/order id
     */
    private Long referenceId;

    /**
     * Optional remarks
     */
    private String remarks;
}