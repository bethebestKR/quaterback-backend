package com.example.quaterback.api.feature.overview.dto.response;

import com.example.quaterback.api.domain.station.constant.StationStatus;
import com.example.quaterback.api.domain.station.domain.ChargingStationDomain;
import lombok.Builder;

@Builder
public record StationOverviewResponse(
        String stationId,
        String stationName,
        double latitude,
        double longitude,
        StationStatus status
) {
    public static StationOverviewResponse from(ChargingStationDomain domain){
        return StationOverviewResponse.builder()
                .stationId(domain.getStationId())
                .stationName(domain.getModel())
                .latitude(domain.getLatitude())
                .longitude(domain.getLongitude())
                .status(domain.getStationStatus())
                .build();
    }
}
