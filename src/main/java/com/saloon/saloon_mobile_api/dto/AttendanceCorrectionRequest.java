package com.saloon.saloon_mobile_api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttendanceCorrectionRequest {
    private Long attendanceId;
    private LocalDateTime requestedCheckOut;
    private String reason;
}
