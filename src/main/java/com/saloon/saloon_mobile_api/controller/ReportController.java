package com.saloon.saloon_mobile_api.controller;


import com.saloon.saloon_mobile_api.dto.ReportResponse;
import com.saloon.saloon_mobile_api.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ReportController {


    private final ReportService service;


    @GetMapping
    public ReportResponse report(

            @RequestParam LocalDateTime from,

            @RequestParam LocalDateTime to

    ) {


        return service.generate(
                from,
                to
        );


    }


}
