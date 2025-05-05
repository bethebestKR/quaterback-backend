package com.example.quaterback.api.feature.managing.dto;

import com.example.quaterback.api.domain.customer.domain.CustomerDomain;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerDetailResponseDto {
    private String customerId;
    private String customerName;
    private String idToken;
    private String phone;
    private String email;
    private String vehicleNo;
    private String registrationDate;

    public static CustomerDetailResponseDto fromCustomerDomain(CustomerDomain customerDomain) {
        return CustomerDetailResponseDto.builder()
                .customerId(customerDomain.getCustomerId())
                .customerName(customerDomain.getCustomerName())
                .phone(customerDomain.getPhone())
                .email(customerDomain.getEmail())
                .vehicleNo(customerDomain.getVehicleNo())
                .registrationDate(customerDomain.getRegistrationDate())
                .idToken(customerDomain.getIdToken())
                .build();
    }
}
