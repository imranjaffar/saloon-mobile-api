package com.saloon.saloon_mobile_api.service;



import com.saloon.saloon_mobile_api.dto.AppointmentRequest;
import com.saloon.saloon_mobile_api.dto.AppointmentResponse;
import com.saloon.saloon_mobile_api.entity.Appointment;

import java.util.List;


public interface AppointmentService {



    AppointmentResponse create(
            AppointmentRequest request
    );



    AppointmentResponse updateStatus(
            Long id,
            String status
    );



    List<AppointmentResponse> getAll();



    List<AppointmentResponse> today();



    void delete(Long id);


}
