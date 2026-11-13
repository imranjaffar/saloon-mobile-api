package com.saloon.saloon_mobile_api.security;

import com.saloon.saloon_mobile_api.entity.Role;
import com.saloon.saloon_mobile_api.entity.User;
import com.saloon.saloon_mobile_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.default.admin.username}")
    private String adminUsername;

    @Value("${app.default.admin.password}")
    private String adminPassword;

    @Value("${app.default.employee.username}")
    private String employeeUsername;

    @Value("${app.default.employee.password}")
    private String employeePassword;

    @Override
    public void run(String... args) {
        createDefaultUserIfMissing(adminUsername, adminPassword, Role.ADMIN);
        createDefaultUserIfMissing(employeeUsername, employeePassword, Role.EMPLOYEE);
    }

    private void createDefaultUserIfMissing(String username, String rawPassword, Role role) {
        if (userRepository.findByName(username).isPresent()) {
            return;
        }

        User user = User.builder()
                .name(username)
                .password(passwordEncoder.encode(rawPassword))
                .role(role)
                .build();

        userRepository.save(user);

        log.info("Default {} created: {}/{}", role.name().toLowerCase(), username, rawPassword);
    }
}