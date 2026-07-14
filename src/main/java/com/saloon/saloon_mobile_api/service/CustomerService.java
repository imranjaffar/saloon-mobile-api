package com.saloon.saloon_mobile_api.service;




import com.saloon.saloon_mobile_api.entity.Customer;

import java.util.List;


public interface CustomerService {


    Customer create(Customer customer);


    List<Customer> getAll();


    Customer get(Long id);


    Customer update(
            Long id,
            Customer customer
    );


    void delete(Long id);


}
