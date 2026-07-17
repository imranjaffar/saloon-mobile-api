package com.saloon.saloon_mobile_api.controller;



import com.saloon.saloon_mobile_api.dto.ChangePasswordRequest;
import com.saloon.saloon_mobile_api.dto.UserResponse;
import com.saloon.saloon_mobile_api.entity.User;
import com.saloon.saloon_mobile_api.security.JwtUtil;
import com.saloon.saloon_mobile_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;



@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {


    private final UserService service;


    @PostMapping("/create")
    public UserResponse create(@RequestBody User user) {
        return service.createUser(user);
    }



    // 🔥 GET ALL USERS
    @GetMapping
    public List<UserResponse> getAll() {
        return service.getAllUsers();
    }

    // 🔥 ENABLE / DISABLE
    @PostMapping("/status")
    public UserResponse updateStatus(
            @RequestParam Long id,
            @RequestParam boolean enabled
    ) {
        return service.toggleStatus(id, enabled);
    }

    @PostMapping("/enable/{id}")
    public UserResponse enableUser(
            @PathVariable Long id) {

        return service.toggleStatus(id, true);

    }

    @PostMapping("/disable/{id}")
    public UserResponse disableUser(
            @PathVariable Long id) {
        return service.toggleStatus(id, false);
    }

//    @PostMapping("/change-password")
//    public String changePassword(@RequestBody ChangePasswordRequest req,
//                                 @RequestHeader("Authorization") String token) {
//        String username = JwtUtil.extractUsername(token.substring(7));
//
//        return service.changePassword(username, req.oldPassword, req.newPassword);
//    }


    @GetMapping("/non-employees")
    public List<UserResponse> getUsersWithoutEmployee() {

        return service.getUsersWithoutEmployee();

    }


}