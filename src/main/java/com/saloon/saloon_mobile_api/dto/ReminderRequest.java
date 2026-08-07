package com.saloon.saloon_mobile_api.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReminderRequest {

    private Long customerId;

    private Long appointmentId;

    private Long serviceId;

    private LocalDate reminderDate;

    private String message;

}