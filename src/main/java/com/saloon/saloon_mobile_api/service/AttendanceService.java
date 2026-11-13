package com.saloon.saloon_mobile_api.service;



import com.saloon.saloon_mobile_api.dto.AttendanceRequest;
import com.saloon.saloon_mobile_api.dto.AttendanceResponse;
import com.saloon.saloon_mobile_api.entity.Attendance;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface AttendanceService {

    AttendanceResponse checkOut(String userName);

    List<AttendanceResponse> getMyAttendance(String username);
    List<AttendanceResponse> getMyAttendance(
            String username,
            Pageable pageable
    );

    AttendanceResponse checkIn(String userName);

    AttendanceResponse checkOut(
            Long employeeId,
            String username,
            String reason
    );

    AttendanceResponse markAttendance(
            AttendanceRequest request
    );

    List<AttendanceResponse> getTodayAttendance();
    List<AttendanceResponse> getTodayAttendance(Pageable pageable);

    List<AttendanceResponse> getAttendanceByEmployee(
            Long employeeId
    );
    List<AttendanceResponse> getAttendanceByEmployee(
            Long employeeId,
            Pageable pageable
    );

    List<AttendanceResponse> getAttendanceByDate(
            LocalDate date
    );
    List<AttendanceResponse> getAttendanceByDate(
            LocalDate date,
            Pageable pageable
    );

}
