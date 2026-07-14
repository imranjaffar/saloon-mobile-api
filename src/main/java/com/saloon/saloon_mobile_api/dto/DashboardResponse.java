package com.saloon.saloon_mobile_api.dto;



import lombok.Builder;
import lombok.Data;




import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    // TODAY
    private double todayIncome;
    private double todayExpense;
    private double profit;

    private long todayAppointments;
    private long completedAppointments;
    private long cancelledAppointments;

    // MONTH
    private double monthlyIncome;
    private double monthlyExpense;

    // BUSINESS KPIs
    private long totalCustomers;
    private long totalEmployees;
}
