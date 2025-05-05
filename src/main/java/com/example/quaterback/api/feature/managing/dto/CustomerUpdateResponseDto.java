package com.example.quaterback.api.feature.managing.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerUpdateResponseDto {
    private String customerId;

    public static CustomerUpdateResponseDto fromCustomerId(String customerId) {
        return CustomerUpdateResponseDto.builder()
                .customerId(customerId)
                .build();
    }
}
