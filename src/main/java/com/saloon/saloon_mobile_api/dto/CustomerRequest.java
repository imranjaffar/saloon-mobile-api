package com.saloon.saloon_mobile_api.dto;



import lombok.Data;


@Data
public class CustomerRequest {


    private String name;

    private String mobile;

    private String email;

    private String gender;

    private String notes;


}