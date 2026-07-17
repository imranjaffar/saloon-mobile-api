package com.saloon.saloon_mobile_api.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {

    public String username;
    public String oldPassword;
    public String newPassword;
}
