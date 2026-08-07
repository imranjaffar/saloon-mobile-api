package com.saloon.saloon_mobile_api.service;




import com.saloon.saloon_mobile_api.dto.ServiceRequest;
import com.saloon.saloon_mobile_api.dto.ServiceResponse;
import com.saloon.saloon_mobile_api.entity.Services;

import java.util.List;


public interface  SaloonService {


    ServiceResponse create(
            ServiceRequest request
    );


    ServiceResponse update(
            Long id,
            ServiceRequest request
    );


    void delete(Long id);


    List<ServiceResponse> getAll();


    ServiceResponse getById(Long id);


}
