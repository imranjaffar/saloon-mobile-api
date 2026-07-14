package com.saloon.saloon_mobile_api.dto;


import com.saloon.saloon_mobile_api.entity.AttendanceStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class AttendanceRequest {

    private Long employeeId;

    private LocalDate date;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    private AttendanceStatus status;

}
