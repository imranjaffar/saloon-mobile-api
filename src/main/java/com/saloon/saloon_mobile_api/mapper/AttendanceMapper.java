package com.saloon.saloon_mobile_api.mapper;

import com.saloon.saloon_mobile_api.dto.AttendanceResponse;
import com.saloon.saloon_mobile_api.entity.Attendance;
import org.springframework.stereotype.Component;

@Component
public class AttendanceMapper {

    public  AttendanceResponse toResponse(Attendance attendance) {

        if (attendance == null) return null;

        return AttendanceResponse.builder()
                .id(attendance.getId())

                .employeeId(
                        attendance.getEmployee() != null
                                ? attendance.getEmployee().getId()
                                : null
                )

                .employeeName(
                        attendance.getEmployee() != null
                                ? attendance.getEmployee().getUser().getName()
                                : null
                )

                .checkIn(attendance.getCheckIn())
                .checkOut(attendance.getCheckOut())
                .status(attendance.getStatus() != null
                        ? attendance.getStatus().name()
                        : null
                )

                .build();
    }
}