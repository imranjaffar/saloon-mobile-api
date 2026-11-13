package com.saloon.saloon_mobile_api.service;




import com.saloon.saloon_mobile_api.dto.CreateEmployeeRequest;
import com.saloon.saloon_mobile_api.dto.EmployeeResponse;
import com.saloon.saloon_mobile_api.dto.UpdateEmployeeRequest;
import com.saloon.saloon_mobile_api.entity.Employee;

import java.util.List;
import org.springframework.data.domain.Pageable;


public interface EmployeeService {


    EmployeeResponse create(CreateEmployeeRequest employee);


    List<EmployeeResponse> getAll();
    List<EmployeeResponse> getPage(Pageable pageable);
    List<EmployeeResponse> getPage(Pageable pageable, String search);


    EmployeeResponse get(Long id);


    // UPDATE
    EmployeeResponse update(Long id, UpdateEmployeeRequest employee);

    void delete(Long id);


}
