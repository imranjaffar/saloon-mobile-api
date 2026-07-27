package com.saloon.saloon_mobile_api.controller;

import com.saloon.saloon_mobile_api.entity.User;
import com.saloon.saloon_mobile_api.repository.UserRepository;
import com.saloon.saloon_mobile_api.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User request) {

        User user = repo.findByName(request.getName())
                .orElse(null);

        Map<String, Object> response = new HashMap<>();

        if (user == null) {
            response.put("error", "Invalid username");
            return response;
        }

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            response.put("error", "Invalid password");
            return response;
        }

        String token = JwtUtil.generateToken(
                user.getName(),
                user.getRole().name()
        );

        response.put("token", token);
        response.put("username", user.getName());
        response.put("role", user.getRole());

        return response;
    }
}