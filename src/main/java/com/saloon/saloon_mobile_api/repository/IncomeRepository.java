package com.saloon.saloon_mobile_api.repository;




import com.saloon.saloon_mobile_api.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;



public interface IncomeRepository
        extends JpaRepository<Income,Long> {


    @Query("""
SELECT COALESCE(SUM(i.amount), 0)
FROM Income i
WHERE DATE(i.createdAt) BETWEEN :from AND :to
""")
    double totalIncome(LocalDate from, LocalDate to);




    @Query("""
SELECT COALESCE(SUM(i.amount),0)
FROM Income i
WHERE i.createdAt >= :start
AND i.createdAt < :end
""")
    double todayIncome(LocalDateTime start,
                       LocalDateTime end);

    @Query("""
    SELECT COALESCE(SUM(i.amount), 0)
    FROM Income i
    WHERE i.createdAt >= :startOfMonth
      AND i.createdAt < :startOfNextMonth
    """)
    double monthlyIncome(LocalDateTime startOfMonth,
                         LocalDateTime startOfNextMonth);


    @Query("SELECT COALESCE(SUM(i.amount),0) FROM Income i")
    BigDecimal sumIncome();



}
