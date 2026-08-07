package com.saloon.saloon_mobile_api.entity;




import com.saloon.saloon_mobile_api.service.AppointmentService;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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



//    @Column(nullable=false)
//    private String customerName;
//
//
//
//    private String customerPhone;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customer_id")
    private Customer customer;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="employee_id",
            nullable=false
    )
    private Employee employee;





    @OneToMany(
            mappedBy = "appointment",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<AppointmentSaloonService> services = new ArrayList<>();





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

    @Column(nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal totalAmount = BigDecimal.ZERO;


}
