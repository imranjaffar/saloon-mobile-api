package com.saloon.saloon_mobile_api.service;



import com.saloon.saloon_mobile_api.dto.AppointmentRequest;
import com.saloon.saloon_mobile_api.dto.AppointmentResponse;
import com.saloon.saloon_mobile_api.entity.Appointment;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface AppointmentService {



    AppointmentResponse create(
            AppointmentRequest request
    );



    AppointmentResponse updateStatus(
            Long id,
            String status
    );



    List<AppointmentResponse> getAll(Pageable pageable);



    List<AppointmentResponse> today(Pageable pageable);



    void delete(Long id);


}
