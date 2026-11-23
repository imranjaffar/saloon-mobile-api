package com.saloon.saloon_mobile_api.service;

import com.saloon.saloon_mobile_api.dto.ReminderRequest;
import com.saloon.saloon_mobile_api.dto.ReminderResponse;
import com.saloon.saloon_mobile_api.entity.*;
import com.saloon.saloon_mobile_api.repository.AppointmentRepository;
import com.saloon.saloon_mobile_api.repository.CustomerRepository;
import com.saloon.saloon_mobile_api.repository.ReminderRepository;
import com.saloon.saloon_mobile_api.repository.SalonServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReminderService {

    private final ReminderRepository reminderRepository;

    private final CustomerRepository customerRepository;

    private final AppointmentRepository appointmentRepository;

    private final SalonServiceRepository salonServiceRepository;

    public ReminderResponse create(ReminderRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Appointment appointment = null;

//        if (request.getAppointmentId() != null) {
//            appointment = appointmentRepository.findById(request.getAppointmentId())
//                    .orElseThrow(() -> new RuntimeException("Appointment not found"));
//        }

        Services service = null;

        if (request.getServiceId() != null) {
            service = salonServiceRepository.findById(request.getServiceId())
                    .orElseThrow(() -> new RuntimeException("Service not found"));
        }

        Reminder reminder = Reminder.builder()
                .customer(customer)
                .appointment(appointment)
                .service(service)
                .reminderDate(request.getReminderDate())
                .message(request.getMessage())
                .status(ReminderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        reminder = reminderRepository.save(reminder);

        return map(reminder);
    }

    public List<ReminderResponse> getAll() {
        return reminderRepository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    public List<ReminderResponse> getToday() {
        return reminderRepository.findByReminderDateAndStatus(
                        java.time.LocalDate.now(),
                        ReminderStatus.PENDING)
                .stream()
                .map(this::map)
                .toList();
    }

    public List<ReminderResponse> getPending() {
        return reminderRepository.findByStatus(ReminderStatus.PENDING)
                .stream()
                .map(this::map)
                .toList();
    }

    public List<ReminderResponse> getPending(Pageable pageable) {
        return reminderRepository.findByStatus(
                        ReminderStatus.PENDING,
                        pageable
                )
                .map(this::map)
                .toList();
    }

    public List<ReminderResponse> getHistory(Pageable pageable) {
        return reminderRepository.findByStatusNot(
                        ReminderStatus.PENDING,
                        pageable
                )
                .map(this::map)
                .toList();
    }

    public List<ReminderResponse> getCustomer(Long customerId) {
        return reminderRepository.findByCustomerId(customerId)
                .stream()
                .map(this::map)
                .toList();
    }

    public ReminderResponse complete(Long id) {

        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reminder not found"));

        reminder.setStatus(ReminderStatus.COMPLETED);
        reminder.setUpdatedAt(LocalDateTime.now());

        return map(reminderRepository.save(reminder));
    }

    public ReminderResponse cancel(Long id) {

        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reminder not found"));

        reminder.setStatus(ReminderStatus.CANCELLED);
        reminder.setUpdatedAt(LocalDateTime.now());

        return map(reminderRepository.save(reminder));
    }

    public void delete(Long id) {
        reminderRepository.deleteById(id);
    }

    private ReminderResponse map(Reminder reminder) {

        return ReminderResponse.builder()
                .id(reminder.getId())

                .customerId(reminder.getCustomer().getId())
                .customerName(reminder.getCustomer().getName())

                .appointmentId(
                        reminder.getAppointment() != null
                                ? reminder.getAppointment().getId()
                                : null)

                .serviceId(
                        reminder.getService() != null
                                ? reminder.getService().getId()
                                : null)

                .serviceName(
                        reminder.getService() != null
                                ? reminder.getService().getName()
                                : null)

                .reminderDate(reminder.getReminderDate())
                .message(reminder.getMessage())
                .status(reminder.getStatus())
                .createdAt(reminder.getCreatedAt())
                .updatedAt(reminder.getUpdatedAt())
                .build();
    }


    public List<ReminderResponse> getDueReminders() {

        return reminderRepository.findByStatus(ReminderStatus.DUE)
                .stream()
                .map(this::map)
                .toList();
    }

    public void markCompleted(Long id) {

        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow();

        reminder.setStatus(ReminderStatus.COMPLETED);

        reminderRepository.save(reminder);
    }


}