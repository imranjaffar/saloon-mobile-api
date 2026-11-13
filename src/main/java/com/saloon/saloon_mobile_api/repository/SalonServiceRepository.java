package com.saloon.saloon_mobile_api.repository;



import com.saloon.saloon_mobile_api.entity.Services;
import com.saloon.saloon_mobile_api.service.SaloonService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface SalonServiceRepository
        extends JpaRepository<Services,Long> {


    List<Services> findByActiveTrue();
    Page<Services> findByActiveTrue(Pageable pageable);


    boolean existsByName(String name);


}
