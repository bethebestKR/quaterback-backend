package com.example.quaterback.api.feature.managing.dto;

import com.example.quaterback.api.domain.customer.domain.CustomerDomain;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerResponseDto {
    private String customerId;
    private String customerName;
    private String idToken;
    private String vehicleNo;
    private String registrationDate;

    public static CustomerResponseDto fromDomain(CustomerDomain customerDomain) {
        return CustomerResponseDto.builder()
                .customerId(customerDomain.getCustomerId())
                .customerName(customerDomain.getCustomerName())
                .idToken(customerDomain.getIdToken())
                .vehicleNo(customerDomain.getVehicleNo())
                .registrationDate(customerDomain.getRegistrationDate())
                .build();
    }
}
