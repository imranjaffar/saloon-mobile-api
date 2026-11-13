package com.saloon.saloon_mobile_api.controller;

import com.saloon.saloon_mobile_api.dto.AttendanceResponse;
import com.saloon.saloon_mobile_api.dto.AdminAttendanceCheckOutRequest;
import com.saloon.saloon_mobile_api.dto.AttendanceCorrectionRequest;
import com.saloon.saloon_mobile_api.dto.AttendanceCorrectionResponse;
import com.saloon.saloon_mobile_api.dto.AttendanceCorrectionReviewRequest;
import com.saloon.saloon_mobile_api.service.AttendanceCorrectionService;
import com.saloon.saloon_mobile_api.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import com.saloon.saloon_mobile_api.utility.PaginationSupport;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/attendances")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final AttendanceCorrectionService correctionService;

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
    // Admin CHECK-OUT
    // POST /attendances/{employeeId}/check-out
    // =====================================
    @PostMapping("/{employeeId}/check-out")
    public AttendanceResponse adminCheckOut(
            @PathVariable Long employeeId,
            @RequestBody AdminAttendanceCheckOutRequest request,
            Authentication authentication) {

        return attendanceService.checkOut(
                employeeId,
                authentication.getName(),
                request == null ? null : request.getReason()
        );
    }



    @PostMapping("/corrections")
    public AttendanceCorrectionResponse requestCorrection(
            @RequestBody AttendanceCorrectionRequest request,
            Authentication authentication) {
        return correctionService.request(authentication.getName(), request);
    }

    @GetMapping("/corrections/mine")
    public List<AttendanceCorrectionResponse> myCorrectionRequests(
            Authentication authentication) {
        return correctionService.getMyRequests(authentication.getName());
    }

    @GetMapping("/corrections/pending")
    public List<AttendanceCorrectionResponse> pendingCorrectionRequests() {
        return correctionService.getPendingRequests();
    }

    @PostMapping("/corrections")
    public AttendanceCorrectionResponse requestCorrection(
            @RequestBody AttendanceCorrectionRequest request,
            Authentication authentication) {
        return correctionService.request(authentication.getName(), request);
    }

    @GetMapping("/corrections/mine")
    public List<AttendanceCorrectionResponse> myCorrectionRequests(
            Authentication authentication) {
        return correctionService.getMyRequests(authentication.getName());
    }

    @GetMapping("/corrections/mine/page")
    public List<AttendanceCorrectionResponse> myCorrectionRequestsPage(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return correctionService.getMyRequests(
                authentication.getName(),
                PaginationSupport.page(page, size)
        );
    }

    @GetMapping("/corrections/pending")
    public List<AttendanceCorrectionResponse> pendingCorrectionRequests() {
        return correctionService.getPendingRequests();
    }

    @GetMapping("/corrections/pending/page")
    public List<AttendanceCorrectionResponse> pendingCorrectionRequestsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return correctionService.getPendingRequests(
                PaginationSupport.page(page, size)
        );
    }

    @GetMapping("/corrections/history")
    public List<AttendanceCorrectionResponse> correctionHistory() {
        return correctionService.getHistory();
    }

    @GetMapping("/corrections/history/page")
    public List<AttendanceCorrectionResponse> correctionHistoryPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return correctionService.getHistory(
                PaginationSupport.page(page, size)
        );
    }

    @PostMapping("/corrections/{correctionId}/approve")
    public AttendanceCorrectionResponse approveCorrection(
            @PathVariable Long correctionId,
            @RequestBody(required = false)
            AttendanceCorrectionReviewRequest request,
            Authentication authentication) {
        return correctionService.approve(
                correctionId,
                authentication.getName(),
                request == null ? null : request.getReviewNote()
        );
    }

    @PostMapping("/corrections/{correctionId}/reject")
    public AttendanceCorrectionResponse rejectCorrection(
            @PathVariable Long correctionId,
            @RequestBody(required = false)
            AttendanceCorrectionReviewRequest request,
            Authentication authentication) {
        return correctionService.reject(
                correctionId,
                authentication.getName(),
                request == null ? null : request.getReviewNote()
        );
    @GetMapping("/corrections/history")
    public List<AttendanceCorrectionResponse> correctionHistory() {
        return correctionService.getHistory();
    }

    @PostMapping("/corrections/{correctionId}/approve")
    public AttendanceCorrectionResponse approveCorrection(
            @PathVariable Long correctionId,
            @RequestBody(required = false)
            AttendanceCorrectionReviewRequest request,
            Authentication authentication) {
        return correctionService.approve(
                correctionId,
                authentication.getName(),
                request == null ? null : request.getReviewNote()
        );
    }

    @PostMapping("/corrections/{correctionId}/reject")
    public AttendanceCorrectionResponse rejectCorrection(
            @PathVariable Long correctionId,
            @RequestBody(required = false)
            AttendanceCorrectionReviewRequest request,
            Authentication authentication) {
        return correctionService.reject(
                correctionId,
                authentication.getName(),
                request == null ? null : request.getReviewNote()
        );
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

    @GetMapping("/getEmployeeAttendance/page")
    public List<AttendanceResponse> myAttendancePage(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PaginationSupport.page(page, size);
        return attendanceService.getMyAttendance(
                authentication.getName(),
                pageable
        );
    }

    // =====================================
    // Admin - TODAY ATTENDANCE
    // GET /attendances/today
    // =====================================
    @GetMapping("/today")
    public List<AttendanceResponse> todayAttendance() {

        return attendanceService.getTodayAttendance();
    }

    @GetMapping("/today/page")
    public List<AttendanceResponse> todayAttendancePage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return attendanceService.getTodayAttendance(
                PaginationSupport.page(page, size)
        );
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

    @GetMapping("/employee/{employeeId}/page")
    public List<AttendanceResponse> employeeAttendancePage(
            @PathVariable Long employeeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return attendanceService.getAttendanceByEmployee(
                employeeId,
                PaginationSupport.page(page, size)
        );
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

    @GetMapping("/date/page")
    public List<AttendanceResponse> attendanceByDatePage(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return attendanceService.getAttendanceByDate(
                date,
                PaginationSupport.page(page, size)
        );
    }
}