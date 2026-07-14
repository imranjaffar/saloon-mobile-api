package com.saloon.saloon_mobile_api.entity;



import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;



@Entity
@Table(name="expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense extends BaseEntity {



//    @Id
//    @GeneratedValue(
//            strategy = GenerationType.IDENTITY
//    )
//    private Long id;



    private String title;



    private String description;



    @Column(nullable=false)
    private BigDecimal amount;




    private LocalDateTime expenseDate;



    private String category;




    @PrePersist
    public void created(){

        expenseDate =
                LocalDateTime.now();

    }

}
