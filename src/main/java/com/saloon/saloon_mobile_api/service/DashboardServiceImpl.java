package com.saloon.saloon_mobile_api.service;



import com.saloon.saloon_mobile_api.dto.DashboardResponse;
import com.saloon.saloon_mobile_api.entity.AppointmentStatus;
import com.saloon.saloon_mobile_api.repository.*;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final IncomeRepository incomeRepo;
    private final ExpenseRepository expenseRepo;
    private final AppointmentRepository appointmentRepo;
    private final CustomerRepository customerRepo;
    private final EmployeeRepository employeeRepo;

    @Override
    public DashboardResponse getDashboard() {

        LocalDate today = LocalDate.now();

        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay().minusNanos(1);


        LocalDateTime startOfMonth = today.withDayOfMonth(1).atStartOfDay();
        LocalDateTime startOfNextMonth = startOfMonth.plusMonths(1);

        BigDecimal todayIncome = incomeRepo.totalIncome(start,end);
        BigDecimal todayExpense = expenseRepo.totalExpenseBetweenRange(start,end);

        BigDecimal monthlyIncome = incomeRepo.totalIncome(startOfMonth,startOfNextMonth);
        BigDecimal monthlyExpense = expenseRepo.totalExpenseBetweenRange(startOfMonth,startOfNextMonth);

        return DashboardResponse.builder()

                // TODAY
                .todayIncome(todayIncome)
                .todayExpense(todayExpense)
                .profit(todayIncome .subtract(todayExpense))

                .todayAppointments(
                        appointmentRepo.countByAppointmentDate(LocalDate.now())
                )

                .completedAppointments(
                        appointmentRepo.countByAppointmentDateAndStatus(
                                LocalDate.now(), AppointmentStatus.COMPLETED
                        )
                )

                .cancelledAppointments(
                        appointmentRepo.countByStatus(AppointmentStatus.CANCELLED)
                )

                // MONTH
                .monthlyIncome(monthlyIncome)
                .monthlyExpense(monthlyExpense)

                // TOTALS
                .totalCustomers(customerRepo.count())
                .totalEmployees(employeeRepo.count())

                .build();
    }
}