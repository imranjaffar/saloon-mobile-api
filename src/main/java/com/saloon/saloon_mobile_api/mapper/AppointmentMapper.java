package com.saloon.saloon_mobile_api.mapper;


import com.saloon.saloon_mobile_api.dto.AppointmentResponse;
import com.saloon.saloon_mobile_api.entity.Appointment;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public AppointmentResponse toResponse(Appointment appointment) {

        return AppointmentResponse.builder()
                .id(appointment.getId())

                .customerName(appointment.getCustomerName())
                .customerPhone(appointment.getCustomerPhone())

                .employeeId(
                        appointment.getEmployee() != null
                                ? appointment.getEmployee().getId()
                                : null
                )
                .employeeName(
                        appointment.getEmployee() != null
                                ? appointment.getEmployee().getUser().getName()
                                : null
                )

                .serviceId(
                        appointment.getServices() != null
                                ? appointment.getServices().getId()
                                : null
                )
                .serviceName(
                        appointment.getServices() != null
                                ? appointment.getServices().getName()
                                : null
                )

                .appointmentDate(appointment.getAppointmentDate())
                .appointmentTime(appointment.getAppointmentTime())

                .status(appointment.getStatus())

                .notes(appointment.getNotes())

                .createdAt(appointment.getCreatedAt())

                .build();
    }
}