package com.saloon.saloon_mobile_api.service;


import com.saloon.saloon_mobile_api.dto.ReportResponse;
import com.saloon.saloon_mobile_api.entity.Expense;
import com.saloon.saloon_mobile_api.entity.Income;
import com.saloon.saloon_mobile_api.repository.AppointmentRepository;
import com.saloon.saloon_mobile_api.repository.ExpenseRepository;
import com.saloon.saloon_mobile_api.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final IncomeRepository incomeRepo;
    private final ExpenseRepository expenseRepo;
    private final AppointmentRepository appointmentRepo;

    @Override
    public ReportResponse generate(LocalDate from, LocalDate to) {

        // =========================
        // INCOME FILTER
        // =========================
        List<Income> incomes = incomeRepo.findAll();

        double income = incomes.stream()
                .filter(i -> {
                    LocalDate date = i.getCreatedAt().toLocalDate();
                    return (!date.isBefore(from) && !date.isAfter(to));
                })
                .mapToDouble(Income::getAmount)
                .sum();


        // =========================
        // EXPENSE FILTER
        // =========================
        List<Expense> expenses = expenseRepo.findAll();

        double expense = expenses.stream()
                .filter(e -> {
                    LocalDate date = e.getExpenseDate().toLocalDate();
                    return (!date.isBefore(from) && !date.isAfter(to));
                })
                .mapToDouble(Expense::getAmount)
                .sum();


        // =========================
        // APPOINTMENTS (FIXED)
        // =========================
        long appointments = appointmentRepo
                .countByAppointmentDateBetween(from, to);


        // =========================
        // RESPONSE
        // =========================
        return ReportResponse.builder()
                .income(income)
                .expense(expense)
                .profit(income - expense)
                .appointments(appointments)
                .build();
    }


}