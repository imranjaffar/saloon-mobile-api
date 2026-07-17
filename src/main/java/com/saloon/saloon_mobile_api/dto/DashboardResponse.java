package com.saloon.saloon_mobile_api.dto;



import lombok.Builder;
import lombok.Data;




import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    // TODAY
    private BigDecimal todayIncome;
    private BigDecimal todayExpense;
    private BigDecimal profit;

    private long todayAppointments;
    private long completedAppointments;
    private long cancelledAppointments;

    // MONTH
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyExpense;

    // BUSINESS KPIs
    private long totalCustomers;
    private long totalEmployees;
}
