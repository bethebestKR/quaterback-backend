package com.example.quaterback.api.feature.monitoring.dto.response;

import com.example.quaterback.api.feature.dashboard.dto.query.ChargerUsageQuery;
import com.example.quaterback.api.feature.monitoring.dto.query.ChargingRecordQuery;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChargingRecordResponse(
        LocalDateTime startTime,
        LocalDateTime endTime,
        Double priceKRW,
        String transactionId
) {
    public static ChargingRecordResponse from(ChargingRecordQuery query){
        return ChargingRecordResponse.builder()
                .startTime(query.getStartedTime())
                .endTime(query.getEndedTime())
                .priceKRW(query.getPrice())
                .transactionId(query.getTransactionId())
                .build();
    }

}