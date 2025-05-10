package com.example.quaterback.api.feature.managing.dto.response;

import com.example.quaterback.api.domain.customer.domain.CustomerDomain;
import lombok.Builder;

@Builder
public record CustomerResponse(
        String customerId,
        String customerName,
        String idToken,
        String vehicleNo,
        String registrationDate
) {
    public static CustomerResponse fromDomain(CustomerDomain customerDomain) {
        return CustomerResponse.builder()
                .customerId(customerDomain.getCustomerId())
                .customerName(customerDomain.getCustomerName())
                .idToken(customerDomain.getIdToken())
                .vehicleNo(customerDomain.getVehicleNo())
                .registrationDate(customerDomain.getRegistrationDate())
                .build();
    }
}
