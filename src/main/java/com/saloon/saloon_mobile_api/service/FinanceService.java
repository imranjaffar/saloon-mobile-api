package com.saloon.saloon_mobile_api.service;




import com.saloon.saloon_mobile_api.dto.*;
import com.saloon.saloon_mobile_api.entity.Appointment;
import com.saloon.saloon_mobile_api.entity.Expense;
import com.saloon.saloon_mobile_api.entity.Income;
import com.saloon.saloon_mobile_api.entity.InventoryTransaction;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Pageable;



public interface FinanceService {



    IncomeResponse addIncome(
            IncomeRequest request
    );



    ExpenseResponse addExpense(
            ExpenseRequest request
    );



    List<IncomeResponse> getIncome();
    List<IncomeResponse> getIncome(Pageable pageable);



    List<ExpenseResponse> getExpenses();
    List<ExpenseResponse> getExpenses(Pageable pageable);



    BigDecimal totalIncome();



    BigDecimal totalExpense();



    BigDecimal profit();


    void createAppointmentIncome(Appointment appointment);

    void createStockInExpense(
            InventoryTransaction transaction,
            StockInRequest request);
}
