package com.saloon.saloon_mobile_api.controller;

import com.saloon.saloon_mobile_api.service.CustomerService;
import lombok.*;

import com.saloon.saloon_mobile_api.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;



@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {


    private final CustomerService service;



    @PostMapping
    public Customer create(
            @RequestBody Customer c
    ){

        return service.create(c);

    }



    @GetMapping
    public List<Customer> all(){

        return service.getAll();

    }



    @GetMapping("/{id}")
    public Customer get(
            @PathVariable Long id
    ){

        return service.get(id);

    }



    @PutMapping("/{id}")
    public Customer update(
            @PathVariable Long id,
            @RequestBody Customer c
    ){

        return service.update(id,c);

    }



    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id
    ){

        service.delete(id);

    }


}
