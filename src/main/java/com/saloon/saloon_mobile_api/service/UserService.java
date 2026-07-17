package com.saloon.saloon_mobile_api.service;



import com.saloon.saloon_mobile_api.dto.UserResponse;
import com.saloon.saloon_mobile_api.entity.User;

import java.util.List;


public interface UserService {


    UserResponse createUser(User user);

    // 🔥 LOGIN
//    User login(String username, String password);

    // 🔥 GET ALL USERS
    List<UserResponse> getAllUsers();

    void enableUser(Long id);

    void disableUser(Long id);

    String changePassword(String username, String oldPassword, String newPassword);

    UserResponse toggleStatus(Long id, boolean status);

    List<UserResponse> getUsersWithoutEmployee();
}