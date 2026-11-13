package com.saloon.saloon_mobile_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class StockAdjustmentRequest {

    @NotNull
    private Long inventoryId;

    @NotNull(message = "New quantity is required.")
    @PositiveOrZero(message = "New quantity cannot be negative.")
    private BigDecimal newQuantity;

    @NotNull(message = "Quantity mode is required.")
    private QuantityMode quantityMode;

    private String reason;
}
