package com.saloon.saloon_mobile_api.security;


import com.saloon.saloon_mobile_api.entity.Role;
import com.saloon.saloon_mobile_api.entity.User;
import com.saloon.saloon_mobile_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;



    @Override
    public void run(String... args) {


        createAdmin();

        createEmployee();

    }




    private void createAdmin(){


        if(userRepository.findByName("admin").isEmpty()){


            User admin = User.builder()

                    .name("admin")

                    .password(
                            passwordEncoder.encode("admin123")
                    )

                    .role(Role.ADMIN)

                    .build();



            userRepository.save(admin);



            System.out.println(
                    "Default admin created : admin / admin123"
            );

        }

    }






    private void createEmployee(){


        if(userRepository.findByName("employee").isEmpty()){


            User employee = User.builder()

                    .name("employee")

                    .password(
                            passwordEncoder.encode("employee123")
                    )

                    .role(Role.EMPLOYEE)

                    .build();



            userRepository.save(employee);



            System.out.println(
                    "Default EMPLOYEE created : employee / employee123"
            );

        }

    }

}