package com.saloon.saloon_mobile_api.repository;

import com.saloon.saloon_mobile_api.entity.Reminder;
import com.saloon.saloon_mobile_api.entity.ReminderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    /**
     * Pending reminders
     */
    List<Reminder> findByStatus(ReminderStatus status);

    /**
     * Today's reminders
     */
    List<Reminder> findByReminderDate(LocalDate reminderDate);

    /**
     * Pending reminders for today
     */
    List<Reminder> findByReminderDateAndStatus(
            LocalDate reminderDate,
            ReminderStatus status
    );

    /**
     * Customer reminders
     */
    List<Reminder> findByCustomerId(Long customerId);

    /**
     * Appointment reminders
     */
    List<Reminder> findByAppointmentId(Long appointmentId);

    /**
     * Service reminders
     */
    List<Reminder> findByServiceId(Long serviceId);




}