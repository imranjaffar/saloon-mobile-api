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


        createOwner();

        createEmployee();

    }




    private void createOwner(){


        if(userRepository.findByName("owner").isEmpty()){


            User owner = User.builder()

                    .name("owner")

                    .password(
                            passwordEncoder.encode("owner123")
                    )

                    .role(Role.OWNER)

                    .build();



            userRepository.save(owner);



            System.out.println(
                    "Default OWNER created : owner / owner123"
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