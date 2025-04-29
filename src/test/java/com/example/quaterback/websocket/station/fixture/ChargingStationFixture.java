package com.example.quaterback.websocket.station.fixture;

import com.example.quaterback.station.constant.StationStatus;
import com.example.quaterback.station.entity.ChargingStationEntity;

import java.time.LocalDateTime;

public class ChargingStationFixture {
    public static ChargingStationEntity createStationEntity() {
        return ChargingStationEntity.builder()
                .stationId("station-001")
                .stationStatus(StationStatus.ACTIVE)
                .model("m1")
                .vendorId("quarterback")
                .updateStatusTimeStamp(LocalDateTime.of(2025,4,10,8,8,8))
                .address("서울")
                .latitude(37.55)
                .longitude(126.55)
                .build();
    }
}
