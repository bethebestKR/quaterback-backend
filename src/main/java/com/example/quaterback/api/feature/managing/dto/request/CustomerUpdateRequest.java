package com.example.quaterback.api.feature.managing.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CustomerUpdateRequest {
    @NotBlank(message = "이름을 입력하십시오.")
    private String customerName;
    @NotBlank(message = "전화번호를 입력하십시오.")
    private String phone;

    @NotBlank(message = "이메일을 입력하십시오.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;
    @NotBlank(message = "차량 번호를 입력하십시오.")
    private String vehicleNo;
}
