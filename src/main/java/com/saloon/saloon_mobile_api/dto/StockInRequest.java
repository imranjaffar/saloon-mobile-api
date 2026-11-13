package com.saloon.saloon_mobile_api.dto;

import com.saloon.saloon_mobile_api.entity.ReferenceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockInRequest {

    @NotNull(message = "Inventory item is required.")
    private Long inventoryId;

    @NotNull(message = "Quantity is required.")
    @Positive(message = "Quantity must be greater than zero.")
    private BigDecimal quantity;

    @NotNull(message = "Quantity mode is required.")
    private QuantityMode quantityMode;

    /**
     * In UNIT mode: price per base unit (ML, GM, or PCS)
     * In PACKAGE mode: price per package (e.g., 1 bottle / 1 box)
     */
    @NotNull(message = "Unit price is required.")
    @Positive(message = "Unit price must be greater than zero.")
    private BigDecimal unitPrice;

    @NotBlank(message = "Supplier name is required.")
    private String supplierName;

    @NotNull(message = "Reference type is required.")
    private ReferenceType referenceType;

    private Long referenceId;
    private String remarks;
}
