package com.saloon.saloon_mobile_api.repository;



import com.saloon.saloon_mobile_api.entity.Employee;
import com.saloon.saloon_mobile_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface EmployeeRepository
        extends JpaRepository<Employee,Long> {

    Optional<Employee> findById(Long employeeId);

    boolean existsByUser(User user);

    Optional<Employee> findByUser(User user);
    Page<Employee> findByUser_NameContainingIgnoreCaseOrDesignationContainingIgnoreCase(
            String username,
            String designation,
            Pageable pageable
    );

}