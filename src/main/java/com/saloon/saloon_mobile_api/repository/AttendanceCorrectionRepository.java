package com.saloon.saloon_mobile_api.repository;

import com.saloon.saloon_mobile_api.entity.Attendance;
import com.saloon.saloon_mobile_api.entity.AttendanceCorrection;
import com.saloon.saloon_mobile_api.entity.AttendanceCorrectionStatus;
import com.saloon.saloon_mobile_api.entity.User;
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
}
