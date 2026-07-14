package com.saloon.saloon_mobile_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateEmployeeRequest {

    @NotNull
    private Long userId;

    @NotBlank
    private String designation;

    @Positive
    private BigDecimal salary;

    @NotBlank
    private String address;

}