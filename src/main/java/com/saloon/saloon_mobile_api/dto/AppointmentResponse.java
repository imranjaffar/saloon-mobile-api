package com.saloon.saloon_mobile_api.dto;

import com.saloon.saloon_mobile_api.entity.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponse {

    private Long id;

    private String customerName;

    private String customerPhone;

    private Long employeeId;

    private String employeeName;

    private List<AppointmentServiceResponse> services;

    private BigDecimal totalAmount;

    private LocalDate appointmentDate;

    private LocalTime appointmentTime;

    private AppointmentStatus status;

    private String notes;

    private LocalDateTime createdAt;
}