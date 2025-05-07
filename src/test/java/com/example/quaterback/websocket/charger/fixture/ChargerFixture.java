package com.example.quaterback.websocket.charger.fixture;

import com.example.quaterback.api.domain.charger.constant.ChargerStatus;
import com.example.quaterback.api.domain.charger.entity.ChargerEntity;
import com.example.quaterback.api.domain.station.entity.ChargingStationEntity;

import java.time.LocalDateTime;

public class ChargerFixture {
    public static ChargerEntity createChargerEntity(ChargingStationEntity stationEntity, ChargerStatus status, LocalDateTime dateTime) {
        return ChargerEntity.builder()
                .chargerStatus(status)
                .evseId(1)
                .updateStatusTimeStamp(dateTime)
                .station(stationEntity)
                .build();
    }
}
