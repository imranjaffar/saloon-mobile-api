package com.saloon.saloon_mobile_api.dto;

import lombok.*;

import java.time.LocalDateTime;


import com.saloon.saloon_mobile_api.entity.AppointmentStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentResponse {

    private Long id;

    private String customerName;

    private String customerPhone;

    private Long employeeId;
    private String employeeName;

    private Long serviceId;
    private String serviceName;

    private LocalDate appointmentDate;

    private LocalTime appointmentTime;

    private AppointmentStatus status;

    private String notes;

    private LocalDateTime createdAt;
}
