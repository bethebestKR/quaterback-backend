package com.example.quaterback.api.domain.customer.domain;

import com.example.quaterback.api.feature.managing.dto.CustomerUpdateRequestDto;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerDomain {
    private String customerId;
    private String customerName;
    private String idToken;
    private String email;
    private String phone;
    private String vehicleNo;
    private String registrationDate;

    public static CustomerDomain fromCustomerUpdateRequestDto(String customerId, CustomerUpdateRequestDto dto) {
        return CustomerDomain.builder()
                .customerId(customerId)
                .customerName(dto.getCustomerName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .vehicleNo(dto.getVehicleNo())
                .build();
    }
}
