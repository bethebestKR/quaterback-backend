package com.example.quaterback.api.feature.monitoring.dto.response;

import com.example.quaterback.api.domain.charger.constant.ChargerStatus;
import com.example.quaterback.api.domain.charger.domain.ChargerDomain;
import com.example.quaterback.api.domain.station.domain.ChargingStationDomain;
import lombok.Builder;

@Builder
public record EvseIdResponse(
        Integer evseId,
        ChargerStatus chargerStatus
) {
    public static EvseIdResponse from(ChargerDomain domain){
        return EvseIdResponse.builder()
                .evseId(domain.getEvseId())
                .chargerStatus(domain.getChargerStatus())
                .build();
    }
}
