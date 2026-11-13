package com.saloon.saloon_mobile_api.service;




import com.saloon.saloon_mobile_api.dto.CustomerResponse;
import com.saloon.saloon_mobile_api.entity.Customer;

import java.util.List;
import org.springframework.data.domain.Pageable;


public interface CustomerService {


    CustomerResponse create(Customer customer);


    List<CustomerResponse> getAll();
    List<CustomerResponse> getPage(Pageable pageable);
    List<CustomerResponse> getPage(Pageable pageable, String search);


    CustomerResponse get(Long id);


    CustomerResponse update(
            Long id,
            Customer customer
    );


    void delete(Long id);


}
