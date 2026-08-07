    package com.saloon.saloon_mobile_api.service;



    import com.saloon.saloon_mobile_api.dto.AppointmentRequest;
    import com.saloon.saloon_mobile_api.dto.AppointmentResponse;
    import com.saloon.saloon_mobile_api.entity.*;
    import com.saloon.saloon_mobile_api.mapper.AppointmentMapper;
    import com.saloon.saloon_mobile_api.repository.*;
    import lombok.RequiredArgsConstructor;

    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;


    import java.math.BigDecimal;
    import java.util.ArrayList;
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

        private final ReminderRepository reminderRepository;


        @Override
        public AppointmentResponse create(AppointmentRequest request) {

            Customer customer = validateAndCreateCustomer(request);

            Employee employee = employeeRepo.findById(request.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));

            Appointment appointment = Appointment.builder()
                    .customer(customer)
                    .employee(employee)
                    .appointmentDate(request.getDate())
                    .appointmentTime(request.getTime())
                    .notes(request.getNotes())
                    .status(AppointmentStatus.BOOKED)
                    .build();

            BigDecimal totalAmount = BigDecimal.ZERO;

            List<AppointmentSaloonService> appointmentServices = new ArrayList<>();

            for (Long serviceId : request.getServiceIds()) {

                Services service = serviceRepo.findById(serviceId)
                        .orElseThrow(() -> new RuntimeException("Service not found"));

                AppointmentSaloonService appointmentService =
                        AppointmentSaloonService.builder()
                                .appointment(appointment)
                                .service(service)
                                .price(service.getPrice())
                                .build();

                appointmentServices.add(appointmentService);

                totalAmount = totalAmount.add(service.getPrice());
            }

            appointment.setServices(appointmentServices);
            appointment.setTotalAmount(totalAmount);

            Appointment saved = appointmentRepo.save(appointment);

            return mapper.toResponse(saved);
        }

        private Customer validateAndCreateCustomer(AppointmentRequest request) {

            Customer customer;

            if (request.getCustomerId() != null) {

                customer = customerRepository.findById(request.getCustomerId())
                        .orElseThrow(() ->
                                new IllegalArgumentException("Customer not found."));

            } else {

                if (request.getCustomerName() == null ||
                        request.getCustomerName().isBlank()) {

                    throw new IllegalArgumentException("Customer name is required.");
                }

                if (request.getCustomerPhone() == null ||
                        request.getCustomerPhone().isBlank()) {

                    throw new IllegalArgumentException("Customer phone is required.");
                }

                customer = new Customer();
                customer.setName(request.getCustomerName().trim());
                customer.setMobile(request.getCustomerPhone().trim());

                customer = customerRepository.save(customer);
            }

            return customer;
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

            if (AppointmentStatus.COMPLETED.equals(updated.getStatus())) {

                financeService.createAppointmentIncome(updated);

                Customer customer = updated.getCustomer();

                for (AppointmentSaloonService appointmentService : updated.getServices()) {

                    Services service = appointmentService.getService();

                    if (service.getRevisitDays() != null) {

                        Reminder reminder = Reminder.builder()
                                .customer(customer)
                                .service(service)
                                .appointment(updated)
                                .reminderDate(
                                        updated.getAppointmentDate()
                                                .plusDays(service.getRevisitDays())
                                )
                                .status(ReminderStatus.PENDING)
                                .build();

                        reminderRepository.save(reminder);
                    }
                }
            }

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
