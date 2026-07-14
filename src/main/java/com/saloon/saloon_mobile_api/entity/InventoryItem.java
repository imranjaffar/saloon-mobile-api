package com.saloon.saloon_mobile_api.entity;



import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name="inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryItem  extends BaseEntity {


//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;


    @Column(nullable=false)
    private String name;


    private String category;


    private Integer quantity;


    private Integer minimumStock;


    private Double purchasePrice;


    private Double sellingPrice;


    private LocalDateTime createdAt;



    @PrePersist
    public void create(){

        createdAt =
                LocalDateTime.now();

    }


}
