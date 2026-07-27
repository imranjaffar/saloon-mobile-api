package com.saloon.saloon_mobile_api.dto;

import com.saloon.saloon_mobile_api.entity.ReferenceType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class StockDeductionRequest {

    @NotNull(message = "Inventory id is required.")
    private Long inventoryId;

    @NotNull(message = "Quantity is required.")
    @Positive(message = "Quantity must be greater than zero.")
    private BigDecimal quantity;

    /**
     * BASE_UNIT
     * PACKAGE
     */
    @NotNull(message = "Quantity mode is required.")
    private QuantityMode quantityMode;

    /**
     * APPOINTMENT
     * SALE
     * MANUAL
     */
    @NotNull(message = "Reference type is required.")
    private ReferenceType referenceType;

    /**
     * Appointment Id / Sale Id etc.
     */
    private Long referenceId;

    /**
     * Optional remarks
     */
    private String remarks;
}