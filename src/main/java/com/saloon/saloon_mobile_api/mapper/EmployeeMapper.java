package com.saloon.saloon_mobile_api.mapper;

import com.saloon.saloon_mobile_api.dto.EmployeeResponse;
import com.saloon.saloon_mobile_api.entity.Employee;
import com.saloon.saloon_mobile_api.entity.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class EmployeeMapper {

    public  EmployeeResponse toResponse(Employee employee) {

        User user = employee.getUser();

        EmployeeResponse response = new EmployeeResponse();

        response.setId(employee.getId());

        response.setDesignation(employee.getDesignation());
        response.setSalary(employee.getSalary());
        response.setAddress(employee.getAddress());

        if (user != null) {
            response.setUserId(user.getId());
            response.setUsername(user.getName());
            response.setPhone(user.getMobile());
            response.setEmail(user.getEmail());
        }

        return response;
    }
}