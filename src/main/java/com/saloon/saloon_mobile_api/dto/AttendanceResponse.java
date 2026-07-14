package com.saloon.saloon_mobile_api.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceResponse {

    private Long id;

    private Long employeeId;
    private String employeeName;

    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

    private String status;
}
