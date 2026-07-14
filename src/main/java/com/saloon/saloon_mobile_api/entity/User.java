package com.saloon.saloon_mobile_api.entity;



import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="users")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {


    @Column(unique=true)
   private String name;

    private String email;


    private String mobile;


    private String password;



    @Enumerated(EnumType.STRING)
    private Role role;


    private boolean active=true;


}
