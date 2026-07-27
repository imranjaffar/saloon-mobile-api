package com.saloon.saloon_mobile_api.dto;

import com.saloon.saloon_mobile_api.entity.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockResponse {

    private Long inventoryId;

    private String inventoryName;

    private BigDecimal availableQuantity;

    private Unit unit;

    private Boolean lowStock;
}