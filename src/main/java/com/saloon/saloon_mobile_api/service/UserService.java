package com.saloon.saloon_mobile_api.service;



import com.saloon.saloon_mobile_api.dto.UserResponse;
import com.saloon.saloon_mobile_api.entity.User;

import java.util.List;


public interface UserService {


    User create(User user);


    List<User> getAll();


    User getById(Long id);


    void delete(Long id);


    List<UserResponse> getUsersWithoutEmployee();
}