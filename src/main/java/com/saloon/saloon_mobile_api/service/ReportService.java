package com.saloon.saloon_mobile_api.service;


import com.saloon.saloon_mobile_api.dto.ReportResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;


public interface ReportService {


    ReportResponse generate(
            LocalDateTime from,
            LocalDateTime to
    );


}
