package com.saloon.saloon_mobile_api.service;

import com.saloon.saloon_mobile_api.dto.AttendanceRequest;
import com.saloon.saloon_mobile_api.dto.AttendanceResponse;
import com.saloon.saloon_mobile_api.entity.Attendance;
import com.saloon.saloon_mobile_api.entity.AttendanceStatus;
import com.saloon.saloon_mobile_api.entity.Employee;
import com.saloon.saloon_mobile_api.entity.User;
import com.saloon.saloon_mobile_api.mapper.AttendanceMapper;
import com.saloon.saloon_mobile_api.repository.AttendanceRepository;
import com.saloon.saloon_mobile_api.repository.EmployeeRepository;
import com.saloon.saloon_mobile_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl
        implements AttendanceService {


    private final AttendanceRepository attendanceRepository;

    private final EmployeeRepository employeeRepository;


    private final AttendanceMapper mapper;

    private final UserRepository userRepo;


    @Override
    public AttendanceResponse checkOut(String userName) {

        User user = userRepo.findByName(userName)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Employee employee = employeeRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found"));

        Attendance attendance = attendanceRepository
                .findByEmployeeAndAttendanceDate(
                        employee,
                        LocalDate.now()
                )
                .orElseThrow(() ->
                        new RuntimeException("Please check in first."));

        if (attendance.getCheckOut() != null) {
            throw new RuntimeException("Already checked out");
        }

        attendance.setCheckOut(LocalDateTime.now());
        attendance.setCheckOutRecordedBy(user);
        attendance.setCheckOutReason(null);

        Attendance saved = attendanceRepository.save(attendance);

        return mapper.toResponse(saved);
    }

    @Override
    public List<AttendanceResponse> getMyAttendance(String username) {

        User user = userRepo.findByName(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Employee employee = employeeRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found"));

        return attendanceRepository
                .findByEmployeeOrderByAttendanceDateDesc(employee)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<AttendanceResponse> getMyAttendance(
            String username,
            Pageable pageable
    ) {
        User user = userRepo.findByName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Employee employee = employeeRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return attendanceRepository
                .findByEmployeeOrderByAttendanceDateDesc(employee, pageable)
                .map(mapper::toResponse)
                .toList();
    }


    @Override
    public AttendanceResponse checkIn(String userName) {

        User user = userRepo
                .findByName(userName)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found"));

        Employee employee =
                employeeRepository.findByUser(user)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee not found"
                                )
                        );


        LocalDate today =
                LocalDate.now();



        Attendance attendance =
                attendanceRepository
                        .findByEmployeeAndAttendanceDate(
                                employee,
                                today
                        )
                        .orElse(null);



        if(attendance != null
                && attendance.getCheckIn()!=null){

            throw new RuntimeException(
                    "Already checked in"
            );
        }



        if(attendance == null){

            attendance = new Attendance();

            attendance.setEmployee(employee);

            attendance.setAttendanceDate(today);
        }



        attendance.setCheckIn(
                LocalDateTime.now()
        );


        attendance.setStatus(
                AttendanceStatus.PRESENT
        );



   Attendance savedAttendance= attendanceRepository.save(attendance);

       return mapper.toResponse(savedAttendance);

    }







    @Override
    public AttendanceResponse checkOut(
            Long employeeId,
            String userName,
            String reason
    ) {

        String checkOutReason = reason == null ? "" : reason.trim();
        if (checkOutReason.isEmpty()) {
            throw new IllegalArgumentException(
                    "A reason is required for an admin-recorded checkout."
            );
        }
        if (checkOutReason.length() > 500) {
            throw new IllegalArgumentException(
                    "Reason must be 500 characters or fewer."
            );
        }

        Employee employee =
                employeeRepository.findById(employeeId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee not found"
                                )
                        );



        Attendance attendance =
                attendanceRepository
                        .findByEmployeeAndAttendanceDate(
                                employee,
                                LocalDate.now()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "No check-in found"
                                )
                        );



        if(attendance.getCheckOut()!=null){

            throw new RuntimeException(
                    "Already checked out"
            );
        }



        attendance.setCheckOut(
                LocalDateTime.now()
        );

        User admin = userRepo.findByName(userName)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
        attendance.setCheckOutRecordedBy(admin);
        attendance.setCheckOutReason(checkOutReason);

        Attendance updatedAttendance= attendanceRepository.save(attendance);

        return mapper.toResponse(updatedAttendance);

    }








    @Override
    public AttendanceResponse markAttendance(
            AttendanceRequest request) {



        Employee employee =
                employeeRepository.findById(
                                request.getEmployeeId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee not found"
                                )
                        );



        Attendance attendance =
                attendanceRepository
                        .findByEmployeeAndAttendanceDate(
                                employee,
                                request.getDate()
                        )
                        .orElse(new Attendance());



        attendance.setEmployee(employee);

        attendance.setAttendanceDate(
                request.getDate()
        );


        attendance.setStatus(
                request.getStatus()
        );


        attendance.setCheckIn(
                request.getCheckIn()
        );


        attendance.setCheckOut(
                request.getCheckOut()
        );




        Attendance markAttendance= attendanceRepository.save(attendance);

        return mapper.toResponse(markAttendance);

    }







    @Override
    public List<AttendanceResponse> getTodayAttendance(){


        return attendanceRepository
                .findByAttendanceDate(
                        LocalDate.now()
                ).stream()
                .map(mapper::toResponse)
                .toList();

    }

    @Override
    public List<AttendanceResponse> getTodayAttendance(Pageable pageable) {
        return attendanceRepository
                .findByAttendanceDate(LocalDate.now(), pageable)
                .map(mapper::toResponse)
                .toList();
    }








    @Override
    public List<AttendanceResponse> getAttendanceByEmployee(
            Long employeeId){


        return attendanceRepository
                .findByEmployeeId(
                        employeeId
                ).stream()
                .map(mapper::toResponse)
                .toList();

    }

    @Override
    public List<AttendanceResponse> getAttendanceByEmployee(
            Long employeeId,
            Pageable pageable
    ) {
        return attendanceRepository.findByEmployeeId(employeeId, pageable)
                .map(mapper::toResponse)
                .toList();
    }








    @Override
    public List<AttendanceResponse> getAttendanceByDate(
            LocalDate date){


        return attendanceRepository
                .findByAttendanceDate(date).stream()
                .map(mapper::toResponse)
                .toList();

    }

    @Override
    public List<AttendanceResponse> getAttendanceByDate(
            LocalDate date,
            Pageable pageable
    ) {
        return attendanceRepository.findByAttendanceDate(date, pageable)
                .map(mapper::toResponse)
                .toList();
    }

}