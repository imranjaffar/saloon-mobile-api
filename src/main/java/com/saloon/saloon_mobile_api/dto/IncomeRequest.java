package com.saloon.saloon_mobile_api.dto;




import com.saloon.saloon_mobile_api.entity.PaymentType;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class IncomeRequest {


    private String title;


    private String description;


    private BigDecimal amount;


    private PaymentType paymentType;


}
