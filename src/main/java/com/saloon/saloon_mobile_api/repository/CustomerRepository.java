package com.saloon.saloon_mobile_api.repository;




import com.saloon.saloon_mobile_api.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CustomerRepository
        extends JpaRepository<Customer,Long> {


    List<Customer> findByNameContainingIgnoreCase(
            String name
    );
    @Query(
            value = """
                SELECT c
                FROM Customer c
                WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(c.mobile, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(c.email, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                """,
            countQuery = """
                SELECT COUNT(c)
                FROM Customer c
                WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(c.mobile, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(c.email, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                """
    )
    Page<Customer> searchPage(
            @Param("search") String search,
            Pageable pageable
    );


}