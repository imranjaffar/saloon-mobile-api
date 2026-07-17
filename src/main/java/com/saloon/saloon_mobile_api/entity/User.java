package com.saloon.saloon_mobile_api.entity;



import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


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


    private LocalDateTime lastLogin;



    @Enumerated(EnumType.STRING)
    private Role role;


    private boolean active=true;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Employee employee;


}
