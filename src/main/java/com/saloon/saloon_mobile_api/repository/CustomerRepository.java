package com.saloon.saloon_mobile_api.repository;




import com.saloon.saloon_mobile_api.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CustomerRepository
        extends JpaRepository<Customer,Long> {


    List<Customer> findByNameContainingIgnoreCase(
            String name
    );


}