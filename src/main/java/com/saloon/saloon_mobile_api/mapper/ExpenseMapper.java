package com.saloon.saloon_mobile_api.mapper;

import com.saloon.saloon_mobile_api.dto.ExpenseResponse;
import com.saloon.saloon_mobile_api.entity.Expense;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {

    public ExpenseResponse toResponse(Expense expense) {

        ExpenseResponse response = new ExpenseResponse();

        response.setId(expense.getId());
        response.setAmount(expense.getAmount());
        response.setDescription(expense.getDescription());
        response.setExpenseDate(expense.getExpenseDate());

        return response;
    }
}