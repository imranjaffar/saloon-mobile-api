package com.saloon.saloon_mobile_api.controller;


import com.saloon.saloon_mobile_api.dto.CreateEmployeeRequest;
import com.saloon.saloon_mobile_api.dto.EmployeeResponse;
import com.saloon.saloon_mobile_api.dto.UpdateEmployeeRequest;
import com.saloon.saloon_mobile_api.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import com.saloon.saloon_mobile_api.utility.PaginationSupport;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EmployeeController {

    private final EmployeeService service;

    @PostMapping
    public EmployeeResponse create(@Valid @RequestBody CreateEmployeeRequest employee) {
        return service.create(employee);
    }

    @GetMapping
    public List<EmployeeResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/page")
    public List<EmployeeResponse> getPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String search) {
        Pageable pageable = PaginationSupport.page(page, size);
        return service.getPage(pageable, search);
    }

    @GetMapping("/{id}")
    public EmployeeResponse get(@PathVariable Long id) {
        return service.get(id);
    }

    @PutMapping("/{id}")
    public EmployeeResponse update(
            @PathVariable Long id,
            @Valid  @RequestBody UpdateEmployeeRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Employee deleted successfully";
    }
}