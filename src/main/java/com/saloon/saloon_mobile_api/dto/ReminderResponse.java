package com.saloon.saloon_mobile_api.dto;

import com.saloon.saloon_mobile_api.entity.ReminderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class ReminderResponse {

    private Long id;

    private Long customerId;

    private String customerName;

    private Long appointmentId;

    private Long serviceId;

    private String serviceName;

    private LocalDate reminderDate;

    private String message;

    private ReminderStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}