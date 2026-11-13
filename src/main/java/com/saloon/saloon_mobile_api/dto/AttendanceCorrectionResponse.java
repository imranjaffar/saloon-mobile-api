package com.saloon.saloon_mobile_api.dto;

import com.saloon.saloon_mobile_api.entity.AttendanceCorrection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AttendanceCorrectionResponse {
    private Long id;
    private Long attendanceId;
    private Long employeeId;
    private String employeeName;
    private String requestedByName;
    private LocalDateTime originalCheckOut;
    private LocalDateTime requestedCheckOut;
    private String reason;
    private String status;
    private LocalDateTime createdAt;
    private String reviewedByName;
    private LocalDateTime reviewedAt;
    private String reviewNote;

    public static AttendanceCorrectionResponse from(
            AttendanceCorrection correction
    ) {
        return AttendanceCorrectionResponse.builder()
                .id(correction.getId())
                .attendanceId(correction.getAttendance().getId())
                .employeeId(correction.getAttendance().getEmployee().getId())
                .employeeName(correction.getAttendance().getEmployee()
                        .getUser().getName())
                .requestedByName(correction.getRequestedBy().getName())
                .originalCheckOut(correction.getOriginalCheckOut())
                .requestedCheckOut(correction.getRequestedCheckOut())
                .reason(correction.getReason())
                .status(correction.getStatus().name())
                .createdAt(correction.getCreatedAt())
                .reviewedByName(correction.getReviewedBy() == null
                        ? null
                        : correction.getReviewedBy().getName())
                .reviewedAt(correction.getReviewedAt())
                .reviewNote(correction.getReviewNote())
                .build();
    }
}
