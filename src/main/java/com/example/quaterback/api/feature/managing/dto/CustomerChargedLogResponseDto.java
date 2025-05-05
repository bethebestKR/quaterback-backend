package com.example.quaterback.api.feature.managing.dto;

import com.example.quaterback.api.domain.txinfo.domain.TransactionInfoDomain;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerChargedLogResponseDto {
    private String startedTime;
    private String endedTime;
    private String vehicleNo;
    private String transactionId;
    private Integer totalMeterValue;
    private Integer totalPrice;

    public static CustomerChargedLogResponseDto fromTxInfoDomain(TransactionInfoDomain txInfoDomain) {
        return CustomerChargedLogResponseDto.builder()
                .startedTime(txInfoDomain.getStartedTime().toString())
                .endedTime(txInfoDomain.getEndedTime().toString())
                .vehicleNo(txInfoDomain.getVehicleNo())
                .transactionId(txInfoDomain.getTransactionId())
                .totalMeterValue(txInfoDomain.getTotalMeterValue())
                .totalPrice(txInfoDomain.getTotalPrice())
                .build();
    }
}
