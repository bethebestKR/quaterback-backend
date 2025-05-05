package com.example.quaterback.api.feature.managing.dto;

import lombok.Getter;

@Getter
public class CustomerUpdateRequestDto {
    private String customerName;
    private String phone;
    private String email;
    private String vehicleNo;
}
