package com.saloon.saloon_mobile_api.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Duration;


@Entity
@Table(
        name = "attendance",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_employee_date",
                        columnNames = {
                                "employee_id",
                                "attendance_date"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance extends BaseEntity {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="employee_id",
            nullable=false
    )
    private Employee employee;



    @Column(
            name="attendance_date",
            nullable=false
    )
    private LocalDate attendanceDate;



    private LocalDateTime checkIn;


    private LocalDateTime checkOut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "check_out_recorded_by_id")
    private User checkOutRecordedBy;

    @Column(name = "check_out_reason", length = 500)
    private String checkOutReason;


    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private AttendanceStatus status;



    @Column(
            name="working_hours"
    )
    private Double workingHours;



    @PrePersist
    public void onCreate(){

        if(status == null){

            status =
                    AttendanceStatus.PRESENT;
        }

        calculateWorkingHours();
    }





    @PreUpdate
    public void onUpdate(){

        calculateWorkingHours();

    }





    private void calculateWorkingHours(){


        if(checkIn != null &&
                checkOut != null){


            long minutes =
                    Duration.between(
                                    checkIn,
                                    checkOut
                            )
                            .toMinutes();


            this.workingHours =
                    minutes / 60.0;

        }

    }

}