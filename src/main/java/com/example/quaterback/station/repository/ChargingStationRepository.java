package com.example.quaterback.station.repository;

import com.example.quaterback.station.domain.ChargingStationDomain;

public interface ChargingStationRepository {
    ChargingStationDomain findByStationId(String stationId);
    String update(ChargingStationDomain domain);
}
