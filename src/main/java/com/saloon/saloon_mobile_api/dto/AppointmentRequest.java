package com.saloon.saloon_mobile_api.dto;



import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Data
public class AppointmentRequest {

    // Existing customer
    private Long customerId;

    private String customerName;


    private String customerPhone;



    private Long employeeId;



    private List<Long> serviceIds;


    private LocalDate date;



    private LocalTime time;



    private String notes;


}
