package com.saloon.saloon_mobile_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class UpdateConsumptionRequest {


    @NotNull
    @Positive
    private BigDecimal consumptionQuantity;
}