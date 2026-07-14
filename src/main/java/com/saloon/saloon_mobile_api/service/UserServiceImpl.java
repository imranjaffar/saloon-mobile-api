package com.saloon.saloon_mobile_api.service;


import com.saloon.saloon_mobile_api.dto.UserResponse;
import com.saloon.saloon_mobile_api.entity.User;
import com.saloon.saloon_mobile_api.mapper.UserMapper;
import com.saloon.saloon_mobile_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;



@Service
@RequiredArgsConstructor
public class UserServiceImpl
        implements UserService {


    private final UserRepository repo;

    private final PasswordEncoder encoder;

    private final UserMapper userMapper;

    @Override
    public User create(User user){


        user.setPassword(
                encoder.encode(
                        user.getPassword()
                )
        );


        return repo.save(user);

    }



    @Override
    public List<User> getAll(){

        return repo.findAll();

    }



    @Override
    public User getById(Long id){

        return repo.findById(id)
                .orElseThrow(
                        ()->new RuntimeException("User not found")
                );

    }



    @Override
    public void delete(Long id){

        repo.deleteById(id);

    }


    @Override
    public List<UserResponse> getUsersWithoutEmployee() {

        return repo.findByEmployeeIsNull()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }


}