package com.saloon.saloon_mobile_api.entity;




import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;



@Entity
@Table(name="appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment extends BaseEntity {


//    @Id
//    @GeneratedValue(
//            strategy = GenerationType.IDENTITY
//    )
//    private Long id;



    // Customer information only
    // No customer table required

    @Column(nullable=false)
    private String customerName;



    private String customerPhone;




    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="employee_id",
            nullable=false
    )
    private Employee employee;





    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="service_id",
            nullable=false
    )
    private Services services;





    private LocalDate appointmentDate;



    private LocalTime appointmentTime;




    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;




    private String notes;



    private LocalDateTime createdAt;



    @PrePersist
    public void createDate(){

        createdAt =
                LocalDateTime.now();

        if(status==null){

            status =
                    AppointmentStatus.BOOKED;
        }

    }


}
