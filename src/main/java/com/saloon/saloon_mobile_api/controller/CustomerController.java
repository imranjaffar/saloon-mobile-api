package com.saloon.saloon_mobile_api.controller;

import com.saloon.saloon_mobile_api.dto.CustomerResponse;
import com.saloon.saloon_mobile_api.service.CustomerService;
import lombok.*;

import com.saloon.saloon_mobile_api.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import com.saloon.saloon_mobile_api.utility.PaginationSupport;


import java.util.List;



@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CustomerController {


    private final CustomerService service;



    @PostMapping
    public CustomerResponse create(
            @RequestBody Customer c
    ){

        return service.create(c);

    }



    @GetMapping
    public List<CustomerResponse> all(){

        return service.getAll();

    }

    @GetMapping("/page")
    public List<CustomerResponse> page(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String search) {
        Pageable pageable = PaginationSupport.page(page, size);
        return service.getPage(pageable, search);
    }



    @GetMapping("/{id}")
    public CustomerResponse get(
            @PathVariable Long id
    ){

        return service.get(id);

    }



    @PutMapping("/{id}")
    public CustomerResponse update(
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
