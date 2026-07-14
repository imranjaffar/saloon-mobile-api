package com.saloon.saloon_mobile_api.controller;



import com.saloon.saloon_mobile_api.dto.UserResponse;
import com.saloon.saloon_mobile_api.entity.User;
import com.saloon.saloon_mobile_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;



@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService service;



    @PostMapping
    public User create(
            @RequestBody User user
    ){

        return service.create(user);

    }



    @GetMapping
    public List<User> all(){

        return service.getAll();

    }



    @GetMapping("/{id}")
    public User get(
            @PathVariable Long id
    ){

        return service.getById(id);

    }



    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id
    ){

        service.delete(id);

    }


    @GetMapping("/non-employees")
    public List<UserResponse> getUsersWithoutEmployee() {

        return service.getUsersWithoutEmployee();

    }


}