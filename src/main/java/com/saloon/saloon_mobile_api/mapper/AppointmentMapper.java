package com.saloon.saloon_mobile_api.mapper;


import com.saloon.saloon_mobile_api.dto.AppointmentResponse;
import com.saloon.saloon_mobile_api.dto.AppointmentServiceResponse;
import com.saloon.saloon_mobile_api.entity.Appointment;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public AppointmentResponse toResponse(Appointment appointment) {

        return AppointmentResponse.builder()
                .id(appointment.getId())

                .customerName(appointment.getCustomer().getName())
                .customerPhone(appointment.getCustomer().getName())

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

                .services(
                        appointment.getServices()
                                .stream()
                                .map(service -> AppointmentServiceResponse.builder()
                                        .serviceId(service.getService().getId())
                                        .serviceName(service.getService().getName())
                                        .price(service.getPrice())
                                        .build())
                                .toList()
                )

                .totalAmount(appointment.getTotalAmount())

                .appointmentDate(appointment.getAppointmentDate())
                .appointmentTime(appointment.getAppointmentTime())

                .status(appointment.getStatus())

                .notes(appointment.getNotes())

                .createdAt(appointment.getCreatedAt())

                .build();
    }
}