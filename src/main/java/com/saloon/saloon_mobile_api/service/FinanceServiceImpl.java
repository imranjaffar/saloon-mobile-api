package com.saloon.saloon_mobile_api.service;




import com.saloon.saloon_mobile_api.dto.ExpenseRequest;
import com.saloon.saloon_mobile_api.dto.ExpenseResponse;
import com.saloon.saloon_mobile_api.dto.IncomeRequest;
import com.saloon.saloon_mobile_api.dto.IncomeResponse;
import com.saloon.saloon_mobile_api.entity.Appointment;
import com.saloon.saloon_mobile_api.entity.Expense;
import com.saloon.saloon_mobile_api.entity.Income;
import com.saloon.saloon_mobile_api.entity.IncomeSource;
import com.saloon.saloon_mobile_api.mapper.ExpenseMapper;
import com.saloon.saloon_mobile_api.mapper.IncomeMapper;
import com.saloon.saloon_mobile_api.repository.ExpenseRepository;
import com.saloon.saloon_mobile_api.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FinanceServiceImpl
        implements FinanceService {



    private final IncomeRepository incomeRepo;


    private final ExpenseRepository expenseRepo;

    private final IncomeMapper incomeMapper;


    private final ExpenseMapper expenseMapper;



    @Override
    public IncomeResponse addIncome(IncomeRequest request) {

        Income income = Income.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .amount(request.getAmount())
                .paymentType(request.getPaymentType())
                .build();
        Income savedIncome = incomeRepo.save(income);
        return incomeMapper.toResponse(savedIncome);
    }




    @Override
    public ExpenseResponse addExpense(ExpenseRequest request) {

        Expense expense = Expense.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .amount(request.getAmount())
                .category(request.getCategory())
                .build();
        Expense savedExpense = expenseRepo.save(expense);
        return expenseMapper.toResponse(savedExpense);
    }




    @Override
    public List<IncomeResponse> getIncome(){

        List<Income> incomeList= incomeRepo.findAll();
        return incomeList.stream().map(incomeMapper::toResponse).collect(Collectors.toList());

    }




    @Override
    public List<ExpenseResponse> getExpenses(){

        List<Expense> expenseList=expenseRepo.findAll();
        return expenseList.stream().map(expenseMapper::toResponse).collect(Collectors.toList());

    }





    @Override
    public BigDecimal totalIncome(){


        return incomeRepo.sumIncome();

    }




    @Override
    public BigDecimal totalExpense() {

        return expenseRepo.totalExpense();
    }





    @Override
    public BigDecimal profit(){


        return totalIncome().subtract(totalExpense());
    }

    @Override
    public void createAppointmentIncome(Appointment appointment) {

        Income income = new Income();

//        income.setAmount(appointment.getTotalAmount());

        income.setCreatedAt(LocalDateTime.now());

        income.setDescription(
                "Appointment #" +
                        appointment.getId() +
                        " - " +
                        appointment.getCustomerName()
        );

        income.setSource(IncomeSource.APPOINTMENT);

        income.setReferenceId(appointment.getId());

        incomeRepo.save(income);
    }

}
