package com.example.quaterback.websocket.charger.fixture;

import com.example.quaterback.charger.constant.ChargerStatus;
import com.example.quaterback.charger.entity.ChargerEntity;
import com.example.quaterback.station.entity.ChargingStationEntity;

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
