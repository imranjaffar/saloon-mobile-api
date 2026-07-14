package com.saloon.saloon_mobile_api.dto;



import lombok.Data;

import java.math.BigDecimal;


@Data
public class ExpenseRequest {


    private String title;


    private String description;


    private BigDecimal amount;


    private String category;


}
