package com.saloon.saloon_mobile_api.dto;



import lombok.Data;


@Data
public class InventoryRequest {


    private String name;


    private String category;


    private Integer quantity;


    private Integer minimumStock;


    private Double purchasePrice;


    private Double sellingPrice;


}
