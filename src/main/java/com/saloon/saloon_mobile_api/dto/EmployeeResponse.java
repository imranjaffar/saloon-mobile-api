package com.saloon.saloon_mobile_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {

    private Long id;

    private Long userId;

    private String username;

    private String fullName;

    private String phone;

    private String email;

    private String designation;

    private BigDecimal salary;

    private String address;

}