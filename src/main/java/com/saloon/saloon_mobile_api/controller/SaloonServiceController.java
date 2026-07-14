package com.saloon.saloon_mobile_api.controller;



import com.saloon.saloon_mobile_api.dto.ServiceRequest;
import com.saloon.saloon_mobile_api.entity.Services;
import com.saloon.saloon_mobile_api.service.SaloonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.saloon.saloon_mobile_api.dto.ExpenseRequest;
import com.saloon.saloon_mobile_api.dto.IncomeRequest;
import com.saloon.saloon_mobile_api.entity.Expense;
import com.saloon.saloon_mobile_api.entity.Income;
import com.saloon.saloon_mobile_api.service.FinanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;



@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class SaloonServiceController {



    private final SaloonService service;



    @PostMapping
    public Services create(
            @RequestBody ServiceRequest request){

        return service.create(request);

    }




    @PutMapping("/{id}")
    public Services update(
            @PathVariable Long id,
            @RequestBody ServiceRequest request){

        return service.update(id,request);

    }




    @GetMapping
    public List<Services> getAll(){

        return service.getAll();

    }




    @GetMapping("/{id}")
    public Services get(
            @PathVariable Long id){

        return service.getById(id);

    }




    @DeleteMapping("/{id}")
    public String delete(
            @PathVariable Long id){


        service.delete(id);

        return "Deleted";

    }


}
