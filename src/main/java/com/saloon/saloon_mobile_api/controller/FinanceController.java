package com.saloon.saloon_mobile_api.controller;

import com.saloon.saloon_mobile_api.dto.ExpenseRequest;
import com.saloon.saloon_mobile_api.dto.ExpenseResponse;
import com.saloon.saloon_mobile_api.dto.FinanceSummaryResponse;
import com.saloon.saloon_mobile_api.dto.IncomeRequest;
import com.saloon.saloon_mobile_api.dto.IncomeResponse;
import com.saloon.saloon_mobile_api.service.FinanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finance")
@RequiredArgsConstructor
@CrossOrigin("*")
public class FinanceController {

    private final FinanceService service;

    // =====================
    // ADD INCOME
    // =====================

    @PostMapping("/income")
    public IncomeResponse addIncome(
            @Valid @RequestBody IncomeRequest request
    ) {
        return service.addIncome(request);
    }

    // =====================
    // ADD EXPENSE
    // =====================

    @PostMapping("/expense")
    public ExpenseResponse addExpense(
            @Valid @RequestBody ExpenseRequest request
    ) {
        return service.addExpense(request);
    }

    // =====================
    // ALL INCOME
    // =====================

    @GetMapping("/income")
    public List<IncomeResponse> getIncome() {
        return service.getIncome();
    }

    // =====================
    // ALL EXPENSE
    // =====================

    @GetMapping("/expense")
    public List<ExpenseResponse> getExpense() {
        return service.getExpenses();
    }

    // =====================
    // SUMMARY
    // =====================

    @GetMapping("/summary")
    public FinanceSummaryResponse summary() {

        return FinanceSummaryResponse.builder()
                .income(service.totalIncome())
                .expense(service.totalExpense())
                .profit(service.profit())
                .build();
    }
}