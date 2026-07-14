package com.saloon.saloon_mobile_api.controller;


import com.saloon.saloon_mobile_api.dto.DashboardResponse;
import com.saloon.saloon_mobile_api.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {



    private final DashboardService service;



    @GetMapping
    public DashboardResponse dashboard(){

        return service.getDashboard();

    }



}
