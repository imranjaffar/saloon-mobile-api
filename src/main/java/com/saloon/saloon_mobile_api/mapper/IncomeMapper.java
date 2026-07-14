package com.saloon.saloon_mobile_api.mapper;

import com.saloon.saloon_mobile_api.dto.IncomeResponse;
import com.saloon.saloon_mobile_api.entity.Income;
import org.springframework.stereotype.Component;

@Component
public class IncomeMapper {

    public IncomeResponse toResponse(Income income) {

        IncomeResponse response = new IncomeResponse();

        response.setId(income.getId());
        response.setAmount(income.getAmount());
        response.setDescription(income.getDescription());
        response.setIncomeDate(income.getCreatedAt());

        return response;
    }
}