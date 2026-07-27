package com.saloon.saloon_mobile_api.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;

    private String category;

    private String brand;

    private String unit;      // pcs, bottle, ml, gm

    private Double stockQty;

    private Double minimumStock;

    private Double purchasePrice;

    private Double sellingPrice;

    private String supplierName;

    private LocalDate expiryDate;

    private Boolean active = true;
}