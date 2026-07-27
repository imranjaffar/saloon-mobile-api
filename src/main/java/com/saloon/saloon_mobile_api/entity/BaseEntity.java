package com.saloon.saloon_mobile_api.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@MappedSuperclass
public class BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;



    @PrePersist
    void created(){

        createdAt=LocalDateTime.now();

    }



    @PreUpdate
    void updated(){

        updatedAt=LocalDateTime.now();

    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}