package com.example.quaterback.api.feature.managing.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CustomerUpdateRequest {
    private String customerName;
    private String phone;
    private String email;
    private String vehicleNo;
}
