package com.saloon.saloon_mobile_api.controller;

import com.saloon.saloon_mobile_api.dto.AttendanceResponse;
import com.saloon.saloon_mobile_api.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/attendances")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AttendanceController {

    private final AttendanceService attendanceService;

    // =====================================
    // EMPLOYEE SELF CHECK-IN
    // POST /attendances/check-in
    // =====================================
    @PostMapping("/check-in")
    public AttendanceResponse checkIn(Authentication authentication) {

        String username = authentication.getName();

        return attendanceService.checkIn(username);
    }

    // =====================================
    // EMPLOYEE SELF CHECK-OUT
    // POST /attendances/check-out
    // =====================================
    @PostMapping("/check-out")
    public AttendanceResponse checkOut(Authentication authentication) {

        String username = authentication.getName();

        return attendanceService.checkOut(username);
    }

    // =====================================
    // Admin CHECK-IN
    // POST /attendances/{employeeId}/check-in
    // =====================================
    @PostMapping("/{employeeId}/check-in")
    public AttendanceResponse adminCheckIn(
            @PathVariable Long employeeId) {

        return attendanceService.checkIn(employeeId);
    }

    // =====================================
    // Admin CHECK-OUT
    // POST /attendances/{employeeId}/check-out
    // =====================================
    @PostMapping("/{employeeId}/check-out")
    public AttendanceResponse adminCheckOut(
            @PathVariable Long employeeId) {

        return attendanceService.checkOut(employeeId);
    }

    // =====================================
    // LOGGED-IN EMPLOYEE ATTENDANCE
    // GET /attendances/getEmployeeAttendance
    // =====================================
    @GetMapping("/getEmployeeAttendance")
    public List<AttendanceResponse> myAttendance(
            Authentication authentication) {

        return attendanceService.getMyAttendance(
                authentication.getName());
    }

    // =====================================
    // Admin - TODAY ATTENDANCE
    // GET /attendances/today
    // =====================================
    @GetMapping("/today")
    public List<AttendanceResponse> todayAttendance() {

        return attendanceService.getTodayAttendance();
    }

    // =====================================
    // Admin - EMPLOYEE ATTENDANCE
    // GET /attendances/employee/{employeeId}
    // =====================================
    @GetMapping("/employee/{employeeId}")
    public List<AttendanceResponse> employeeAttendance(
            @PathVariable Long employeeId) {

        return attendanceService.getAttendanceByEmployee(employeeId);
    }

    // =====================================
    // Admin - ATTENDANCE BY DATE
    // GET /attendances/date?date=yyyy-MM-dd
    // =====================================
    @GetMapping("/date")
    public List<AttendanceResponse> attendanceByDate(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {

        return attendanceService.getAttendanceByDate(date);
    }
}