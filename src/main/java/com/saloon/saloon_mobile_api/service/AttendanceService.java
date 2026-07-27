package com.saloon.saloon_mobile_api.service;



import com.saloon.saloon_mobile_api.dto.AttendanceRequest;
import com.saloon.saloon_mobile_api.dto.AttendanceResponse;
import com.saloon.saloon_mobile_api.entity.Attendance;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {

    AttendanceResponse checkOut(String userName);

    List<AttendanceResponse> getMyAttendance(String username);

    AttendanceResponse checkIn(Long employeeId);

    AttendanceResponse checkIn(String userName);

    AttendanceResponse checkOut(Long employeeId);

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
