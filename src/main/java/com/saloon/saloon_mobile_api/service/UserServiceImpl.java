package com.saloon.saloon_mobile_api.service;


import com.saloon.saloon_mobile_api.dto.UserResponse;
import com.saloon.saloon_mobile_api.entity.User;
import com.saloon.saloon_mobile_api.mapper.UserMapper;
import com.saloon.saloon_mobile_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl
        implements UserService {


    private final UserRepository repo;

    private final PasswordEncoder encoder;

    private final UserMapper userMapper;



    @Override
    public UserResponse createUser(User user) {

        user.setPassword(encoder.encode(user.getPassword()));

        if (repo.findByName(user.getName()).isPresent()) {
            throw new RuntimeException("User with this name already exists");
        }
        User savedUser=repo.save(user);
        return userMapper.toResponse(savedUser);
    }

//    // 🔥 LOGIN
//    @Override
//    public User login(String username, String password) {
//
//        Optional<User> userOpt = repo.findByName(username);
//
//        if (userOpt.isPresent()) {
//
//            User user = userOpt.get();
//
//            if (encoder.matches(password, user.getPassword())) {
//
//                user.setLastLogin(LocalDateTime.now());
//                repo.save(user);
//
//                return user;
//            }
//        }
//
//        return null;
//    }

    // 🔥 GET ALL USERS
    @Override
    public List<UserResponse> getAllUsers() {
        List<User> userList=repo.findAll();
        return userList.stream().map(userMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getUsersPage(Pageable pageable) {
        return repo.findAll(pageable)
                .map(userMapper::toResponse)
                .toList();
    }



    @Override
    public void enableUser(Long id) {

        User user = repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        user.setActive(true);

        repo.save(user);
    }

    @Override
    public void disableUser(Long id) {

        User user = repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        user.setActive(false);

        repo.save(user);
    }


    @Override
    public String changePassword(String username, String oldPassword, String newPassword) {

        User user = repo.findByName(username).orElse(null);

        if (user == null) {
            return "User not found";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // check old password
        if (!encoder.matches(oldPassword, user.getPassword())) {
            return "Old password is incorrect";
        }

        // update new password
        user.setPassword(encoder.encode(newPassword));
        repo.save(user);

        return "Password changed successfully";
    }

    @Override
    public UserResponse toggleStatus(Long id, boolean status) {

        User user = repo.findById(id).orElse(null);

        if (user != null) {
            user.setActive(status);
            User savedUser=repo.save(user);
            return userMapper.toResponse(savedUser);
        }

        return null;
    }

    @Override
    public List<UserResponse> getUsersWithoutEmployee() {

        return repo.findByEmployeeIsNull()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }
    @Override
    public UserResponse updateUser(Long id, User request) {

        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setMobile(request.getMobile());
        user.setRole(request.getRole());

        // Update password only if provided
        if (request.getPassword() != null &&
                !request.getPassword().trim().isEmpty()) {

            user.setPassword(
                    encoder.encode(request.getPassword()));
        }

        User saved = repo.save(user);

        return userMapper.toResponse(saved);
    }

}