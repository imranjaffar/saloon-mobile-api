package com.saloon.saloon_mobile_api.repository;

import com.saloon.saloon_mobile_api.entity.Attendance;
import com.saloon.saloon_mobile_api.entity.AttendanceCorrection;
import com.saloon.saloon_mobile_api.entity.AttendanceCorrectionStatus;
import com.saloon.saloon_mobile_api.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceCorrectionRepository
        extends JpaRepository<AttendanceCorrection, Long> {

    boolean existsByAttendanceAndStatus(
            Attendance attendance,
            AttendanceCorrectionStatus status
    );

    List<AttendanceCorrection> findByStatusOrderByCreatedAtDesc(
            AttendanceCorrectionStatus status
    );

    List<AttendanceCorrection> findByStatusNotOrderByCreatedAtDesc(
            AttendanceCorrectionStatus status
    );

    List<AttendanceCorrection> findByRequestedByOrderByCreatedAtDesc(
            User requestedBy
    );

    Page<AttendanceCorrection> findByStatus(
            AttendanceCorrectionStatus status,
            Pageable pageable
    );

    Page<AttendanceCorrection> findByStatusNot(
            AttendanceCorrectionStatus status,
            Pageable pageable
    );

    Page<AttendanceCorrection> findByRequestedBy(
            User requestedBy,
            Pageable pageable
    );
}
