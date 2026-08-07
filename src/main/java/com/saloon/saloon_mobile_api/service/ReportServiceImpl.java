package com.saloon.saloon_mobile_api.service;


import com.saloon.saloon_mobile_api.dto.ReportResponse;
import com.saloon.saloon_mobile_api.entity.Expense;
import com.saloon.saloon_mobile_api.entity.Income;
import com.saloon.saloon_mobile_api.repository.AppointmentRepository;
import com.saloon.saloon_mobile_api.repository.ExpenseRepository;
import com.saloon.saloon_mobile_api.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final IncomeRepository incomeRepo;
    private final ExpenseRepository expenseRepo;
    private final AppointmentRepository appointmentRepo;

    @Override
    public ReportResponse generate(LocalDateTime from, LocalDateTime to) {

        // =========================
        // INCOME FILTER
        // =========================
        BigDecimal income = incomeRepo.totalIncome(from, to);


        // =========================
        // EXPENSE FILTER
        // =========================
        BigDecimal expense = expenseRepo.totalExpenseBetweenRange(from, to);


        // =========================
        // APPOINTMENTS (FIXED)
        // =========================
        long appointments = appointmentRepo
                .countByAppointmentDateBetween(from.toLocalDate(), to.toLocalDate());


        // =========================
        // RESPONSE
        // =========================
        return ReportResponse.builder()
                .income(income)
                .expense(expense)
                .profit(income.subtract(expense))
                .appointments(appointments)
                .build();
    }


}