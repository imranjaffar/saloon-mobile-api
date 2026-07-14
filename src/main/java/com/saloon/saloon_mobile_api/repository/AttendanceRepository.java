package com.saloon.saloon_mobile_api.repository;

import com.saloon.saloon_mobile_api.entity.Attendance;
import com.saloon.saloon_mobile_api.entity.AttendanceStatus;
import com.saloon.saloon_mobile_api.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository
        extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByEmployeeAndDate(
            Employee employee,
            LocalDate date
    );

    List<Attendance> findByEmployeeId(Long employeeId);

    List<Attendance> findByDate(LocalDate date);

    long countByDate(LocalDate date);

    long countByDateAndStatus(
            LocalDate date,
            AttendanceStatus status
    );

}
