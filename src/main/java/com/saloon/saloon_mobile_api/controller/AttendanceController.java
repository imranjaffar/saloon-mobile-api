package com.saloon.saloon_mobile_api.controller;

import com.saloon.saloon_mobile_api.dto.AttendanceResponse;
import com.saloon.saloon_mobile_api.entity.Attendance;
import com.saloon.saloon_mobile_api.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/attendances")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    // ======================
    // Check IN
    // ======================
    @PostMapping("/check-in/{employeeId}")
    public AttendanceResponse checkIn(@PathVariable Long employeeId) {
        return attendanceService.checkIn(employeeId);
    }

    // ======================
    // Check OUT
    // ======================
    @PostMapping("/check-out/{employeeId}")
    public AttendanceResponse checkOut(@PathVariable Long employeeId) {
        return attendanceService.checkOut(employeeId);
    }

    // ======================
    // Today Attendance
    // ======================
    @GetMapping("/today")
    public List<AttendanceResponse> todayAttendance() {
        return attendanceService.getTodayAttendance();
    }

    // ======================
    // Employee Attendance
    // ======================
    @GetMapping("/employee/{employeeId}")
    public List<AttendanceResponse> employeeAttendance(@PathVariable Long employeeId) {
        return attendanceService.getAttendanceByEmployee(employeeId);
    }

    // ======================
    // Attendance by Date
    // ======================
    @GetMapping("/date")
    public List<AttendanceResponse> attendanceByDate(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date
    ) {
        return attendanceService.getAttendanceByDate(date);
    }
}