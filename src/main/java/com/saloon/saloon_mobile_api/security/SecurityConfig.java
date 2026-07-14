package com.saloon.saloon_mobile_api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


    private final JwtFilter jwtFilter;


    private static final String OWNER = "OWNER";
    private static final String EMPLOYEE = "EMPLOYEE";


    private static final String[] PUBLIC_ENDPOINTS = {

            "/auth/**",

            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };



    // Owner only modules
    private static final String[] OWNER_ENDPOINTS = {

            "/users/**",
            "/employees/**",

            "/finance/**",
            "/reports/**",

            "/inventory/**",
            "/expenses/**",
            "/income/**"

    };



    // Owner + Employee modules
    private static final String[] OWNER_EMPLOYEE_ENDPOINTS = {

            "/customers/**",
            "/appointments/**",
            "/services/**",

            "/attendance/**",

            "/dashboard/**"

    };



    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {


        return http


                .csrf(AbstractHttpConfigurer::disable)


                .cors(cors ->
                        cors.configurationSource(
                                corsConfigurationSource()
                        )
                )


                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )


                .authorizeHttpRequests(auth -> auth


                        // Public
                        .requestMatchers(PUBLIC_ENDPOINTS)
                        .permitAll()



                        // DELETE only owner
                        .requestMatchers(
                                "/appointments/**/delete",
                                "/customers/**/delete",
                                "/employees/**/delete",
                                "/inventory/**/delete"
                        )
                        .hasAuthority(OWNER)



                        // Owner modules
                        .requestMatchers(
                                OWNER_ENDPOINTS
                        )
                        .hasAuthority(OWNER)



                        // Owner + Employee modules
                        .requestMatchers(
                                OWNER_EMPLOYEE_ENDPOINTS
                        )
                        .hasAnyAuthority(
                                OWNER,
                                EMPLOYEE
                        )


                        .anyRequest()
                        .authenticated()

                )


                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                )


                .build();

    }



    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {

        return configuration.getAuthenticationManager();

    }



    @Bean
    public CorsConfigurationSource corsConfigurationSource() {


        CorsConfiguration configuration =
                new CorsConfiguration();


        configuration.setAllowedOrigins(
                List.of("*")
        );


        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "PATCH",
                        "OPTIONS"
                )
        );


        configuration.setAllowedHeaders(
                List.of("*")
        );


        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();


        source.registerCorsConfiguration(
                "/**",
                configuration
        );


        return source;
    }

}