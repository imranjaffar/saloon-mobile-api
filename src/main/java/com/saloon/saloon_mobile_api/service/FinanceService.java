package com.saloon.saloon_mobile_api.service;




import com.saloon.saloon_mobile_api.dto.ExpenseRequest;
import com.saloon.saloon_mobile_api.dto.ExpenseResponse;
import com.saloon.saloon_mobile_api.dto.IncomeRequest;
import com.saloon.saloon_mobile_api.dto.IncomeResponse;
import com.saloon.saloon_mobile_api.entity.Expense;
import com.saloon.saloon_mobile_api.entity.Income;

import java.math.BigDecimal;
import java.util.List;



public interface FinanceService {



    IncomeResponse addIncome(
            IncomeRequest request
    );



    ExpenseResponse addExpense(
            ExpenseRequest request
    );



    List<IncomeResponse> getIncome();



    List<ExpenseResponse> getExpenses();



    BigDecimal totalIncome();



    BigDecimal totalExpense();



    BigDecimal profit();



}
