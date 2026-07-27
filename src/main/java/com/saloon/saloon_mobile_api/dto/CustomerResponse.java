package com.saloon.saloon_mobile_api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {

    private Long id;

    private String name;

    private String mobile;

    private String email;

    private String gender;

    private String notes;
}