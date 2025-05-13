package com.example.quaterback.api.feature.managing.dto.response;

import com.example.quaterback.api.domain.txinfo.domain.TransactionInfoDomain;
import lombok.Builder;

@Builder
public record CustomerChargedLogResponse(
        String startedTime,
        String endedTime,
        String vehicleNo,
        String transactionId,
        Double totalMeterValue,
        Double totalPrice
) {
    public static CustomerChargedLogResponse fromTxInfoDomain(TransactionInfoDomain txInfoDomain) {
        return CustomerChargedLogResponse.builder()
                .startedTime(txInfoDomain.getStartedTime().toString())
                .endedTime(txInfoDomain.getEndedTime().toString())
                .vehicleNo(txInfoDomain.getVehicleNo())
                .transactionId(txInfoDomain.getTransactionId())
                .totalMeterValue(txInfoDomain.getTotalMeterValue())
                .totalPrice(txInfoDomain.getTotalPrice())
                .build();
    }
}
