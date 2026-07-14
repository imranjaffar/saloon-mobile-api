package com.saloon.saloon_mobile_api.dto;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Builder
public class FinanceSummaryResponse {


    private BigDecimal income;


    private BigDecimal expense;


    private BigDecimal profit;


}