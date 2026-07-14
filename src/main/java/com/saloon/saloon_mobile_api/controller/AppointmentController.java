package com.saloon.saloon_mobile_api.controller;


import com.saloon.saloon_mobile_api.dto.AppointmentRequest;
import com.saloon.saloon_mobile_api.dto.AppointmentResponse;
import com.saloon.saloon_mobile_api.entity.Appointment;
import com.saloon.saloon_mobile_api.service.AppointmentService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AppointmentController {



    private final AppointmentService service;



    @PostMapping
    public AppointmentResponse create(
            @RequestBody AppointmentRequest request
    ){

        return service.create(request);

    }





    @GetMapping
    public List<AppointmentResponse> all(){

        return service.getAll();

    }





    @GetMapping("/today")
    public List<AppointmentResponse> today(){

        return service.today();

    }





    @PutMapping("/{id}/status")
    public AppointmentResponse status(
            @PathVariable Long id,
            @RequestParam String value
    ){

        return service.updateStatus(
                id,
                value.toUpperCase()
        );

    }





    @DeleteMapping("/{id}")
    public String delete(
            @PathVariable Long id
    ){

        service.delete(id);

        return "Deleted";

    }

}