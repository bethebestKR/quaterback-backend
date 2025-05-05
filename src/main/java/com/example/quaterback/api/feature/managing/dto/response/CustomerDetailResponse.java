package com.example.quaterback.api.feature.managing.dto.response;

import com.example.quaterback.api.domain.customer.domain.CustomerDomain;
import lombok.Builder;

@Builder
public record CustomerDetailResponse(
        String customerId,
        String customerName,
        String idToken,
        String phone,
        String email,
        String vehicleNo,
        String registrationDate
) {
    public static CustomerDetailResponse fromCustomerDomain(CustomerDomain customerDomain) {
        return CustomerDetailResponse.builder()
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
