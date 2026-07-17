package com.saloon.saloon_mobile_api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {

    private Long id;

    private String name;

    private String email;

    private String mobile;

    private String role;

    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;
}