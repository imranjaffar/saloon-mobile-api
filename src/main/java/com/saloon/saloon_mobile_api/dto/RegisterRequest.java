package com.saloon.saloon_mobile_api.dto;


import lombok.Data;


@Data
public class RegisterRequest {


    private String name;

    private String email;

    private String mobile;

    private String password;


}