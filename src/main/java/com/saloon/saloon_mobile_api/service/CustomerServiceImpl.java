package com.saloon.saloon_mobile_api.service;






import com.saloon.saloon_mobile_api.dto.CustomerResponse;
import com.saloon.saloon_mobile_api.entity.Customer;
import com.saloon.saloon_mobile_api.mapper.CustomerMapper;
import com.saloon.saloon_mobile_api.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


import java.util.List;



@Service
@RequiredArgsConstructor
public class CustomerServiceImpl
        implements CustomerService {



    private final CustomerRepository repo;


    private final CustomerMapper customerMapper;




    @Override
    public CustomerResponse create(
            Customer customer
    ){

        Customer savedCustomer= repo.save(customer);

        return customerMapper.toResponse(savedCustomer);

    }



    @Override
    public List<CustomerResponse> getAll(){

        List<Customer> customerList= repo.findAll();

       return customerMapper.toResponseList(customerList);

    }



    @Override
    public CustomerResponse get(Long id){

        Customer customerDetail= repo.findById(id)
                .orElseThrow(
                        ()->new RuntimeException("Customer not found")
                );

      return  customerMapper.toResponse(customerDetail);

    }



    @Override
    public CustomerResponse update(
            Long id,
            Customer data
    ){


        Customer old=repo.findById(id).orElseThrow(() ->
                new RuntimeException("Customer not found"));;


        old.setName(data.getName());

        old.setMobile(data.getMobile());

        old.setEmail(data.getEmail());

        old.setGender(data.getGender());

        old.setNotes(data.getNotes());


        Customer updatedCustomer= repo.save(old);

        return customerMapper.toResponse(updatedCustomer);

    }



    @Override
    public void delete(Long id){

        repo.deleteById(id);

    }


}
