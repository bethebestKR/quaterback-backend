package com.example.quaterback.station.repository;

import com.example.quaterback.station.entity.ChargingStationEntity;

public interface ChargingStationRepository {
    ChargingStationEntity findByStationId(String stationId);
}
