package com.saloon.saloon_mobile_api.entity;



import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Getter
@Setter
public class Employee extends BaseEntity {

//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;



    @OneToOne
    @JoinColumn(name="user_id")
    private User user;



    private String designation;


    private BigDecimal salary;


    private String address;


}
