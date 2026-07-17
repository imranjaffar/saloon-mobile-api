package com.saloon.saloon_mobile_api.repository;




import com.saloon.saloon_mobile_api.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;



public interface ExpenseRepository
        extends JpaRepository<Expense,Long> {


//
//    @Query("""
//SELECT COALESCE(SUM(e.amount), 0)
//FROM Expense e
//WHERE DATE(e.expenseDate) BETWEEN :from AND :to
//""")
//    double totalExpenseBetweenRange(LocalDate from, LocalDate to);
//
//




    @Query("""
    SELECT COALESCE(SUM(e.amount), 0)
    FROM Expense e
    WHERE e.createdAt >= :start
      AND e.createdAt < :end
    """)
    BigDecimal totalExpenseBetweenRange(LocalDateTime start,
                        LocalDateTime end);




    @Query("SELECT COALESCE(SUM(e.amount),0) FROM Expense e")
    BigDecimal totalExpense();

}
