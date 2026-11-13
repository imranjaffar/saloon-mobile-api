package com.saloon.saloon_mobile_api.service;



import com.saloon.saloon_mobile_api.dto.AttendanceRequest;
import com.saloon.saloon_mobile_api.dto.AttendanceResponse;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {

    AttendanceResponse checkOut(String userName);

    List<AttendanceResponse> getMyAttendance(String username);

    AttendanceResponse checkIn(String userName);

    AttendanceResponse checkOut(
            Long employeeId,
            String userName,
            String reason
    );

    AttendanceResponse markAttendance(
            AttendanceRequest request
    );

    List<AttendanceResponse> getTodayAttendance();

    List<AttendanceResponse> getAttendanceByEmployee(
            Long employeeId
    );

    List<AttendanceResponse> getAttendanceByDate(
            LocalDate date
    );

}
