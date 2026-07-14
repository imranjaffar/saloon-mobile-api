package com.saloon.saloon_mobile_api.security;

import com.saloon.saloon_mobile_api.entity.User;
import com.saloon.saloon_mobile_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = repo.findByName(username)
                .orElseThrow();

        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                Collections.singleton(
                        new org.springframework.security.core.authority.SimpleGrantedAuthority(user.getRole().toString())
                )
        );
    }
}