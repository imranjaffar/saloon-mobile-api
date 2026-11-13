package com.saloon.saloon_mobile_api.service;

import com.saloon.saloon_mobile_api.dto.AttendanceCorrectionRequest;
import com.saloon.saloon_mobile_api.dto.AttendanceCorrectionResponse;
import com.saloon.saloon_mobile_api.entity.Attendance;
import com.saloon.saloon_mobile_api.entity.AttendanceCorrection;
import com.saloon.saloon_mobile_api.entity.AttendanceCorrectionStatus;
import com.saloon.saloon_mobile_api.entity.Employee;
import com.saloon.saloon_mobile_api.entity.User;
import com.saloon.saloon_mobile_api.repository.AttendanceCorrectionRepository;
import com.saloon.saloon_mobile_api.repository.AttendanceRepository;
import com.saloon.saloon_mobile_api.repository.EmployeeRepository;
import com.saloon.saloon_mobile_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceCorrectionService {

    private final AttendanceCorrectionRepository correctionRepository;
    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    public AttendanceCorrectionResponse request(
            String username,
            AttendanceCorrectionRequest request
    ) {
        if (request == null || request.getAttendanceId() == null) {
            throw new IllegalArgumentException("Attendance id is required.");
        }

        User user = findUser(username);
        Employee employee = employeeRepository.findByUser(user)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "This account is not linked to an employee."
                ));
        Attendance attendance = attendanceRepository
                .findById(request.getAttendanceId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Attendance record not found."
                ));

        if (!Objects.equals(attendance.getEmployee().getId(), employee.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Attendance record not found."
            );
        }
        if (attendance.getCheckOut() == null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "There is no checkout to correct."
            );
        }
        if (correctionRepository.existsByAttendanceAndStatus(
                attendance,
                AttendanceCorrectionStatus.PENDING
        )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A correction request is already pending for this attendance."
            );
        }

        String reason = validateReason(request.getReason(), "A reason is required.");
        validateRequestedCheckOut(
                attendance,
                request.getRequestedCheckOut(),
                attendance.getCheckOut()
        );

        AttendanceCorrection correction = AttendanceCorrection.builder()
                .attendance(attendance)
                .requestedBy(user)
                .originalCheckOut(attendance.getCheckOut())
                .requestedCheckOut(request.getRequestedCheckOut())
                .reason(reason)
                .status(AttendanceCorrectionStatus.PENDING)
                .build();

        return AttendanceCorrectionResponse.from(
                correctionRepository.save(correction)
        );
    }

    @Transactional(readOnly = true)
    public List<AttendanceCorrectionResponse> getMyRequests(String username) {
        User user = findUser(username);
        return correctionRepository
                .findByRequestedByOrderByCreatedAtDesc(user)
                .stream()
                .map(AttendanceCorrectionResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AttendanceCorrectionResponse> getMyRequests(
            String username,
            Pageable pageable
    ) {
        User user = findUser(username);
        return correctionRepository
                .findByRequestedBy(user, pageable)
                .map(AttendanceCorrectionResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AttendanceCorrectionResponse> getPendingRequests() {
        return correctionRepository
                .findByStatusOrderByCreatedAtDesc(
                        AttendanceCorrectionStatus.PENDING
                )
                .stream()
                .map(AttendanceCorrectionResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AttendanceCorrectionResponse> getPendingRequests(
            Pageable pageable
    ) {
        return correctionRepository
                .findByStatus(AttendanceCorrectionStatus.PENDING, pageable)
                .map(AttendanceCorrectionResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AttendanceCorrectionResponse> getHistory() {
        return correctionRepository
                .findByStatusNotOrderByCreatedAtDesc(
                        AttendanceCorrectionStatus.PENDING
                )
                .stream()
                .map(AttendanceCorrectionResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AttendanceCorrectionResponse> getHistory(Pageable pageable) {
        return correctionRepository
                .findByStatusNot(AttendanceCorrectionStatus.PENDING, pageable)
                .map(AttendanceCorrectionResponse::from)
                .toList();
    }

    public AttendanceCorrectionResponse approve(
            Long correctionId,
            String username,
            String reviewNote
    ) {
        AttendanceCorrection correction = findPendingCorrection(correctionId);
        Attendance attendance = correction.getAttendance();

        if (!Objects.equals(
                attendance.getCheckOut(),
                correction.getOriginalCheckOut()
        )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "The checkout changed after this request was submitted."
            );
        }
        validateRequestedCheckOut(
                attendance,
                correction.getRequestedCheckOut(),
                correction.getOriginalCheckOut()
        );

        User reviewer = findUser(username);
        attendance.setCheckOut(correction.getRequestedCheckOut());
        attendance.setCheckOutRecordedBy(
                correction.getRequestedCheckOut() == null ? null : reviewer
        );
        attendance.setCheckOutReason(
                correction.getRequestedCheckOut() == null
                        ? null
                        : correction.getReason()
        );
        attendanceRepository.save(attendance);

        correction.setStatus(AttendanceCorrectionStatus.APPROVED);
        correction.setReviewedBy(reviewer);
        correction.setReviewedAt(LocalDateTime.now());
        correction.setReviewNote(validateOptionalNote(reviewNote));

        return AttendanceCorrectionResponse.from(
                correctionRepository.save(correction)
        );
    }

    public AttendanceCorrectionResponse reject(
            Long correctionId,
            String username,
            String reviewNote
    ) {
        AttendanceCorrection correction = findPendingCorrection(correctionId);
        User reviewer = findUser(username);
        correction.setStatus(AttendanceCorrectionStatus.REJECTED);
        correction.setReviewedBy(reviewer);
        correction.setReviewedAt(LocalDateTime.now());
        correction.setReviewNote(
                validateReason(reviewNote, "A rejection reason is required.")
        );

        return AttendanceCorrectionResponse.from(
                correctionRepository.save(correction)
        );
    }

    private AttendanceCorrection findPendingCorrection(Long correctionId) {
        AttendanceCorrection correction = correctionRepository
                .findById(correctionId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Correction request not found."
                ));

        if (correction.getStatus() != AttendanceCorrectionStatus.PENDING) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "This correction request has already been reviewed."
            );
        }
        return correction;
    }

    private User findUser(String username) {
        return userRepository.findByName(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found."
                ));
    }

    private void validateRequestedCheckOut(
            Attendance attendance,
            LocalDateTime requestedCheckOut,
            LocalDateTime originalCheckOut
    ) {
        if (requestedCheckOut == null) {
            return;
        }
        if (attendance.getCheckIn() != null
                && requestedCheckOut.isBefore(attendance.getCheckIn())) {
            throw new IllegalArgumentException(
                    "Corrected checkout must be after check-in."
            );
        }
        if (requestedCheckOut.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                    "Corrected checkout cannot be in the future."
            );
        }
        if (requestedCheckOut.equals(originalCheckOut)) {
            throw new IllegalArgumentException(
                    "Corrected checkout must be different from the current checkout."
            );
        }
    }

    private String validateReason(String value, String message) {
        String reason = value == null ? "" : value.trim();
        if (reason.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        if (reason.length() > 500) {
            throw new IllegalArgumentException(
                    "Reason must be 500 characters or fewer."
            );
        }
        return reason;
    }

    private String validateOptionalNote(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return validateReason(value, "Review note cannot be blank.");
    }
}
