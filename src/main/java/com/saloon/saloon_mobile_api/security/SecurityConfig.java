package com.saloon.saloon_mobile_api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


    private final JwtFilter jwtFilter;


    private static final String ADMIN = "ADMIN";
    private static final String EMPLOYEE = "EMPLOYEE";


    private static final String[] PUBLIC_ENDPOINTS = {

            "/auth/**"
    };



    // Admin only modules
    private static final String[] ADMIN_ENDPOINTS = {

            "/users/**",
            "/employees/**",

            "/finance/**",
            "/reports/**",
            "/inventory/**",
            "/expenses/**",
            "/income/**"


    };



    // Admin + Employee modules
    private static final String[] ADMIN_EMPLOYEE_ENDPOINTS = {

            "/customers/**",
            "/appointments/**",
            "/services/**",

            "/attendances/**",

            "/dashboard/**"

    };




    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());

        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests(auth -> auth

                .requestMatchers(PUBLIC_ENDPOINTS)
                .permitAll()

                // DELETE only Admin


                .requestMatchers(
                        HttpMethod.DELETE,
                        "/appointments/**",
                        "/customers/**",
                        "/employees/**",
                        "/inventory/**"
                ).hasAuthority(ADMIN)

                // Admin modules
                .requestMatchers(
                        ADMIN_ENDPOINTS
                )
                .hasAuthority(ADMIN)


                // Admin + Employee modules
                .requestMatchers(
                        ADMIN_EMPLOYEE_ENDPOINTS
                )
                .hasAnyAuthority(
                        ADMIN,
                        EMPLOYEE
                )

                .anyRequest().authenticated()
        );

        http.addFilterBefore(jwtFilter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }

}