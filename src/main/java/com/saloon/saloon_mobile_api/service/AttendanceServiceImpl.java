package com.saloon.saloon_mobile_api.service;

import com.saloon.saloon_mobile_api.dto.AttendanceRequest;
import com.saloon.saloon_mobile_api.dto.AttendanceResponse;
import com.saloon.saloon_mobile_api.entity.Attendance;
import com.saloon.saloon_mobile_api.entity.AttendanceStatus;
import com.saloon.saloon_mobile_api.entity.Employee;
import com.saloon.saloon_mobile_api.mapper.AppointmentMapper;
import com.saloon.saloon_mobile_api.mapper.AttendanceMapper;
import com.saloon.saloon_mobile_api.repository.AttendanceRepository;
import com.saloon.saloon_mobile_api.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl
        implements AttendanceService {


    private final AttendanceRepository attendanceRepository;

    private final EmployeeRepository employeeRepository;


    private final AttendanceMapper mapper;


    @Override
    public AttendanceResponse checkIn(Long employeeId) {


        Employee employee =
                employeeRepository.findById(employeeId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee not found"
                                )
                        );


        LocalDate today =
                LocalDate.now();



        Attendance attendance =
                attendanceRepository
                        .findByEmployeeAndDate(
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
    public AttendanceResponse checkOut(Long employeeId) {


        Employee employee =
                employeeRepository.findById(employeeId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee not found"
                                )
                        );



        Attendance attendance =
                attendanceRepository
                        .findByEmployeeAndDate(
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
                        .findByEmployeeAndDate(
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
                .findByDate(
                        LocalDate.now()
                ).stream()
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
    public List<AttendanceResponse> getAttendanceByDate(
            LocalDate date){


        return attendanceRepository
                .findByDate(date).stream()
                .map(mapper::toResponse)
                .toList();

    }


}