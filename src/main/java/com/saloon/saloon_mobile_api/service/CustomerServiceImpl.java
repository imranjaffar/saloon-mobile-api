package com.saloon.saloon_mobile_api.service;






import com.saloon.saloon_mobile_api.entity.Customer;
import com.saloon.saloon_mobile_api.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


import java.util.List;



@Service
@RequiredArgsConstructor
public class CustomerServiceImpl
        implements CustomerService {



    private final CustomerRepository repo;



    @Override
    public Customer create(
            Customer customer
    ){

        return repo.save(customer);

    }



    @Override
    public List<Customer> getAll(){

        return repo.findAll();

    }



    @Override
    public Customer get(Long id){

        return repo.findById(id)
                .orElseThrow(
                        ()->new RuntimeException("Customer not found")
                );

    }



    @Override
    public Customer update(
            Long id,
            Customer data
    ){


        Customer old=get(id);


        old.setName(data.getName());

        old.setMobile(data.getMobile());

        old.setEmail(data.getEmail());

        old.setGender(data.getGender());

        old.setNotes(data.getNotes());


        return repo.save(old);

    }



    @Override
    public void delete(Long id){

        repo.deleteById(id);

    }


}
