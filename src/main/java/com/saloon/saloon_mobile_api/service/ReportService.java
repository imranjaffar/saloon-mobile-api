package com.saloon.saloon_mobile_api.service;


import com.saloon.saloon_mobile_api.dto.ReportResponse;

import java.time.LocalDate;



public interface ReportService {


    ReportResponse generate(
            LocalDate from,
            LocalDate to
    );


}
