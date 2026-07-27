package com.saloon.saloon_mobile_api.service;



import com.saloon.saloon_mobile_api.dto.AppointmentRequest;
import com.saloon.saloon_mobile_api.dto.AppointmentResponse;
import com.saloon.saloon_mobile_api.entity.*;
import com.saloon.saloon_mobile_api.mapper.AppointmentMapper;
import com.saloon.saloon_mobile_api.repository.AppointmentRepository;
import com.saloon.saloon_mobile_api.repository.CustomerRepository;
import com.saloon.saloon_mobile_api.repository.EmployeeRepository;
import com.saloon.saloon_mobile_api.repository.SalonServiceRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;



@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl
        implements AppointmentService {



    private final AppointmentRepository appointmentRepo;


    private final EmployeeRepository employeeRepo;


    private final SalonServiceRepository serviceRepo;


    private final AppointmentMapper mapper;


    private final CustomerRepository customerRepository;

    private final FinanceService financeService;


    @Override
    public AppointmentResponse create(AppointmentRequest request) {

        Customer customer=null;
        validateAndCreateCustomer(request);

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

    private void validateAndCreateCustomer(AppointmentRequest request) {
        Customer customer;
        if (request.getCustomerId() != null) {

            customer = customerRepository.findById(request.getCustomerId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Customer not found."));

        } else {

            if (request.getCustomerName() == null || request.getCustomerName().isBlank()) {
                throw new IllegalArgumentException("Customer name is required.");
            }

            if (request.getCustomerPhone() == null || request.getCustomerPhone().isBlank()) {
                throw new IllegalArgumentException("Customer phone is required.");
            }

            customer = new Customer();
            customer.setName(request.getCustomerName().trim());
            customer.setMobile(request.getCustomerPhone().trim());

            customer = customerRepository.save(customer);
        }
    }


    @Override
    @Transactional
    public AppointmentResponse updateStatus(Long id, String status) {

        Appointment appointment = appointmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        try {
            appointment.setStatus(AppointmentStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status);
        }


        Appointment updated = appointmentRepo.save(appointment);

        //create income entry
        financeService.createAppointmentIncome(appointment);


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
