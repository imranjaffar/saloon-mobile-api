package com.saloon.saloon_mobile_api.controller;



import com.saloon.saloon_mobile_api.dto.ServiceRequest;
import com.saloon.saloon_mobile_api.dto.ServiceResponse;
import com.saloon.saloon_mobile_api.entity.Services;
import com.saloon.saloon_mobile_api.service.SaloonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import com.saloon.saloon_mobile_api.utility.PaginationSupport;
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
@CrossOrigin("*")
public class SaloonServiceController {



    private final SaloonService service;



    @PostMapping
    public ServiceResponse create(
            @RequestBody ServiceRequest request){

        return service.create(request);

    }




    @PutMapping("/{id}")
    public ServiceResponse update(
            @PathVariable Long id,
            @RequestBody ServiceRequest request){

        return service.update(id,request);

    }




    @GetMapping
    public List<ServiceResponse> getAll(){

        return service.getAll();

    }

    @GetMapping("/page")
    public List<ServiceResponse> getPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PaginationSupport.page(page, size);
        return service.getPage(pageable);
    }




    @GetMapping("/{id}")
    public ServiceResponse get(
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
