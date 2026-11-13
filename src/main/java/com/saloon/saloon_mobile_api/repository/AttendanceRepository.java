package com.saloon.saloon_mobile_api.repository;

import com.saloon.saloon_mobile_api.entity.Attendance;
import com.saloon.saloon_mobile_api.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository
        extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByEmployeeAndAttendanceDate(
            Employee employee,
            LocalDate date
    );

    List<Attendance> findByEmployeeId(Long employeeId);
    Page<Attendance> findByEmployeeId(Long employeeId, Pageable pageable);

    List<Attendance> findByAttendanceDate(LocalDate date);
    Page<Attendance> findByAttendanceDate(LocalDate date, Pageable pageable);

    List<Attendance> findByEmployeeOrderByAttendanceDateDesc(
            Employee employee);
    Page<Attendance> findByEmployeeOrderByAttendanceDateDesc(
            Employee employee,
            Pageable pageable
    );






}
