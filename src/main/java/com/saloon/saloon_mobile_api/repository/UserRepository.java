package com.saloon.saloon_mobile_api.repository;



import com.saloon.saloon_mobile_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository
        extends JpaRepository<User,Long>{


    Optional<User> findByName(String name);


    boolean existsByEmail(String email);

    List<User> findByEmployeeIsNull();


}
