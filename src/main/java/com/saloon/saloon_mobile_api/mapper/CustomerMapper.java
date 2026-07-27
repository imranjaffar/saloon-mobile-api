package com.saloon.saloon_mobile_api.mapper;

import com.saloon.saloon_mobile_api.dto.CustomerResponse;
import com.saloon.saloon_mobile_api.entity.Customer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerMapper {

    public CustomerResponse toResponse(Customer customer) {

        if (customer == null) {
            return null;
        }

        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .mobile(customer.getMobile())
                .email(customer.getEmail())
                .gender(customer.getGender())
                .notes(customer.getNotes())
                .build();
    }

    public List<CustomerResponse> toResponseList(List<Customer> customers) {

        return customers.stream()
                .map(this::toResponse)
                .toList();
    }


}