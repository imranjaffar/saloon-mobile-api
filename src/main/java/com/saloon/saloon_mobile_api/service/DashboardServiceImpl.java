package com.saloon.saloon_mobile_api.service;



import com.saloon.saloon_mobile_api.dto.DashboardResponse;
import com.saloon.saloon_mobile_api.repository.*;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;



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

        double todayIncome = incomeRepo.todayIncome(start,end);
        double todayExpense = expenseRepo.todayExpenseBetweenRange(start,end);

        double monthlyIncome = incomeRepo.monthlyIncome(startOfMonth,startOfNextMonth);
        double monthlyExpense = expenseRepo.monthlyExpense(startOfMonth,startOfNextMonth);

        return DashboardResponse.builder()

                // TODAY
                .todayIncome(todayIncome)
                .todayExpense(todayExpense)
                .profit(todayIncome - todayExpense)

                .todayAppointments(
                        appointmentRepo.countByAppointmentDate(LocalDate.now())
                )

                .completedAppointments(
                        appointmentRepo.countByAppointmentDateAndStatus(
                                LocalDate.now(), "COMPLETED"
                        )
                )

                .cancelledAppointments(
                        appointmentRepo.countByStatus("CANCELLED")
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