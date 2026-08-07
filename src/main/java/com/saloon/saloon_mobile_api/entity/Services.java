package com.saloon.saloon_mobile_api.entity;



import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name="salon_services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Services  extends BaseEntity {


//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;


    @Column(nullable = false)
    private String name;


    private String description;


    private Integer durationMinutes;


    @Column(nullable = false)
    private BigDecimal price;


    private boolean active = true;


    private LocalDateTime createdAt;


    @PrePersist
    public void created(){

        createdAt =
                LocalDateTime.now();

    }



    private Integer revisitDays;

}
