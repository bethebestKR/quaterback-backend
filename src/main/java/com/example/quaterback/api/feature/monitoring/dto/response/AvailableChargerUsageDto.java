package com.example.quaterback.api.feature.monitoring.dto.response;

import com.example.quaterback.api.domain.txinfo.domain.TransactionInfoDomain;

import java.time.LocalDateTime;

public record AvailableChargerUsageDto(
        LocalDateTime chargeStartTime,
        LocalDateTime chargeEndTime,
        double chargedEnergy,
        long price,
        String carNumber,
        String chargerModel,
        String approvalNumber,
        String errorCode
) {
    public static AvailableChargerUsageDto from(TransactionInfoDomain domain) {
        return new AvailableChargerUsageDto(
                domain.getStartedTime(),
                domain.getEndedTime(),
                domain.getTotalMeterValue() / 1000.0,
                domain.getTotalPrice(),
                domain.getVehicleNo(),
                domain.getStationId(),     // → chargerModel로 교체 필요 시 수정
                domain.getUserId(),        // → approvalNumber로 교체 필요 시 수정
                "00"                       // → errorCode가 도메인에 없으면 고정값
        );
    }
}