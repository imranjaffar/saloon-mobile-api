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
import org.springframework.data.domain.Pageable;
import com.saloon.saloon_mobile_api.utility.PaginationSupport;

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

    @GetMapping("/income/page")
    public List<IncomeResponse> getIncomePage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PaginationSupport.page(page, size);
        return service.getIncome(pageable);
    }

    // =====================
    // ALL EXPENSE
    // =====================

    @GetMapping("/expense")
    public List<ExpenseResponse> getExpense() {
        return service.getExpenses();
    }

    @GetMapping("/expense/page")
    public List<ExpenseResponse> getExpensePage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PaginationSupport.page(page, size);
        return service.getExpenses(pageable);
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