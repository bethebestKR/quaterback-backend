package com.example.quaterback.api.feature.managing.dto.response;

import lombok.Builder;

@Builder
public record CustomerUpdateResponse(String customerId) {

    public static CustomerUpdateResponse fromCustomerId(String customerId) {
        return CustomerUpdateResponse.builder()
                .customerId(customerId)
                .build();
    }
}
