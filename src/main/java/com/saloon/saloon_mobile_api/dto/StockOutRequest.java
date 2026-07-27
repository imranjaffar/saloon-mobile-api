package com.saloon.saloon_mobile_api.dto;

import com.saloon.saloon_mobile_api.entity.ReferenceType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class StockOutRequest {

    @NotNull(message = "Inventory id is required.")
    private Long inventoryId;

    /**
     * Quantity to deduct.
     *
     * QuantityMode.BASE_UNIT
     * Example:
     * 50 ml
     * 250 gm
     *
     * QuantityMode.PACKAGE
     * Example:
     * 2 Bottles
     * 3 Boxes
     */
    @NotNull(message = "Quantity is required.")
    @Positive(message = "Quantity must be greater than zero.")
    private BigDecimal quantity;

    @NotNull(message = "Quantity mode is required.")
    private QuantityMode quantityMode;

    /**
     * MANUAL
     * SALE
     * APPOINTMENT
     * WASTAGE
     * RETURN
     */
    @NotNull(message = "Reference type is required.")
    private ReferenceType referenceType;

    /**
     * Appointment Id / Sale Id etc.
     * Optional for manual stock out.
     */
    private Long referenceId;

    /**
     * Optional remarks.
     */
    private String remarks;
}