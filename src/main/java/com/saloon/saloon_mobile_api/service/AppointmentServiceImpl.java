package com.saloon.saloon_mobile_api.service;



import com.saloon.saloon_mobile_api.dto.AppointmentRequest;
import com.saloon.saloon_mobile_api.dto.AppointmentResponse;
import com.saloon.saloon_mobile_api.entity.Appointment;
import com.saloon.saloon_mobile_api.entity.AppointmentStatus;
import com.saloon.saloon_mobile_api.entity.Employee;
import com.saloon.saloon_mobile_api.entity.Services;
import com.saloon.saloon_mobile_api.mapper.AppointmentMapper;
import com.saloon.saloon_mobile_api.repository.AppointmentRepository;
import com.saloon.saloon_mobile_api.repository.EmployeeRepository;
import com.saloon.saloon_mobile_api.repository.SalonServiceRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


import java.util.List;



@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl
        implements AppointmentService {



    private final AppointmentRepository appointmentRepo;


    private final EmployeeRepository employeeRepo;


    private final SalonServiceRepository serviceRepo;


    private final AppointmentMapper mapper;



    @Override
    public AppointmentResponse create(AppointmentRequest request) {

        Employee employee = employeeRepo.findById(request.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Services service = serviceRepo.findById(request.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found"));

        Appointment appointment = Appointment.builder()
                .customerName(request.getCustomerName())
                .customerPhone(request.getCustomerPhone())
                .employee(employee)
                .services(service)
                .appointmentDate(request.getDate())
                .appointmentTime(request.getTime())
                .notes(request.getNotes())
                .status(AppointmentStatus.BOOKED)
                .build();


        Appointment saved = appointmentRepo.save(appointment);

        return mapper.toResponse(saved);


    }




    @Override
    public AppointmentResponse updateStatus(Long id, String status) {

        Appointment appointment = appointmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        try {
            appointment.setStatus(AppointmentStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status);
        }


        Appointment updated = appointmentRepo.save(appointment);

        return mapper.toResponse(updated);
    }




    @Override
    public List<AppointmentResponse> getAll(){

        return appointmentRepo.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();

    }




    @Override
    public List<AppointmentResponse> today(){

        return appointmentRepo
                .findByAppointmentDate(
                        java.time.LocalDate.now()
                ).stream()
                .map(mapper::toResponse)
                .toList();

    }




    @Override
    public void delete(Long id){

        appointmentRepo.deleteById(id);

    }


}
