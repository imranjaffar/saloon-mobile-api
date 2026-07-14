package com.saloon.saloon_mobile_api.dto;

import lombok.Data;

@Data
public class UserResponse {

    private Long id;

    private String name;

    private String email;

    private String mobile;

    private String role;
}