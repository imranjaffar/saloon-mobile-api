package com.saloon.saloon_mobile_api.controller;


import com.saloon.saloon_mobile_api.dto.ReportResponse;
import com.saloon.saloon_mobile_api.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;



@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {



    private final ReportService service;



    @GetMapping
    public ReportResponse report(

            @RequestParam LocalDate from,

            @RequestParam LocalDate to

    ){


        return service.generate(
                from,
                to
        );


    }


}
