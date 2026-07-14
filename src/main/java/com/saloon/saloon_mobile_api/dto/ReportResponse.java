package com.saloon.saloon_mobile_api.dto;



import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ReportResponse {


    private Double income;


    private Double expense;


    private Double profit;


    private Long appointments;


}
