package com.saloon.saloon_mobile_api.mapper;

import com.saloon.saloon_mobile_api.dto.ServiceResponse;
import com.saloon.saloon_mobile_api.entity.Services;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper {

    public ServiceResponse toResponse(Services service) {

        ServiceResponse response = new ServiceResponse();

        response.setId(service.getId());
        response.setName(service.getName());
        response.setDescription(service.getDescription());
        response.setDurationMinutes(service.getDurationMinutes());
        response.setPrice(service.getPrice());
        response.setRevisitDays(service.getRevisitDays());
        return response;
    }
}