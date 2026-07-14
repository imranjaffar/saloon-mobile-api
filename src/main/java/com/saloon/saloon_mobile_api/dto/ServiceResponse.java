package com.saloon.saloon_mobile_api.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServiceResponse {

    private Long id;

    private String name;

    private String description;

    private Integer durationMinutes;

    private BigDecimal price;
}