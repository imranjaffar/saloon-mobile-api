package com.saloon.saloon_mobile_api.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ExpenseResponse {

    private Long id;

    private BigDecimal amount;

    private String description;

    private LocalDateTime expenseDate;
}