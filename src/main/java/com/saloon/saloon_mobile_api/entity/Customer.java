package com.saloon.saloon_mobile_api.entity;



import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
public class Customer extends BaseEntity{


    private String name;


    private String mobile;


    private String email;


    private String gender;


    private String notes;


}