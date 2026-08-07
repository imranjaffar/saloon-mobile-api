package com.saloon.saloon_mobile_api.repository;


import com.saloon.saloon_mobile_api.entity.Appointment;
import com.saloon.saloon_mobile_api.entity.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;



public interface AppointmentRepository
        extends JpaRepository<Appointment,Long>{



    List<Appointment>
    findByAppointmentDate(
            LocalDate date
    );



    List<Appointment>
    findByEmployeeId(
            Long employeeId
    );



    List<Appointment>
    findByStatus(
            AppointmentStatus status
    );



    long countByAppointmentDate(
            LocalDate date
    );



    long countByAppointmentDateAndStatus(LocalDate date, AppointmentStatus status);

    long countByStatus(AppointmentStatus status);

    @Query("SELECT COUNT(a) FROM Appointment a WHERE MONTH(a.appointmentDate) = MONTH(CURRENT_DATE) AND YEAR(a.appointmentDate) = YEAR(CURRENT_DATE)")
    long countMonthlyAppointments();


    long countByAppointmentDateBetween(LocalDate from, LocalDate to);
}
