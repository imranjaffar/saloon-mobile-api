package com.saloon.saloon_mobile_api.dto;



import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Builder
public class ReportResponse {


    private BigDecimal income;


    private BigDecimal expense;


    private BigDecimal profit;


    private Long appointments;


}
