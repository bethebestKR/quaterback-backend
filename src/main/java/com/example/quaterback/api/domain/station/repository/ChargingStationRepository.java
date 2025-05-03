package com.example.quaterback.api.domain.station.repository;

import com.example.quaterback.api.domain.station.domain.ChargingStationDomain;

public interface ChargingStationRepository {
    ChargingStationDomain findByStationId(String stationId);
    String update(ChargingStationDomain domain);
}
