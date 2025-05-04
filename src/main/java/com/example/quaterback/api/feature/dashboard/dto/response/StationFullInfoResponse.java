package com.example.quaterback.api.feature.dashboard.dto.response;

import com.example.quaterback.api.feature.dashboard.dto.query.StationFullInfoQuery;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record StationFullInfoResponse(
        String stationId,
        String stationName,
        String address,
        String stationStatus,
        LocalDateTime regDate,
        Long totalChargers,
        Long avaliableCount,
        Long occupiedCount,
        Long unAvaliableCount
) {
    public static StationFullInfoResponse from(StationFullInfoQuery query){
        return StationFullInfoResponse.builder()
                .stationId(query.getStationId())
                .stationName(query.getModel())
                .address(query.getAddress())
                .stationStatus(query.getStationStatus().name())
                .regDate(query.getUpdateStatusTimeStamp())
                .totalChargers(query.getChargerCount())
                .avaliableCount(query.getActiveCount())
                .occupiedCount(query.getErrorCount())
                .unAvaliableCount(query.getDisconnectedCount())
                .build();
    }
}
