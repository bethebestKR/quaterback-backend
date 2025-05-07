package com.example.quaterback.api.domain.station.repository;

import com.example.quaterback.api.domain.station.domain.ChargingStationDomain;
import com.example.quaterback.api.domain.station.entity.ChargingStationEntity;

import java.util.List;

public interface ChargingStationRepository {
    ChargingStationDomain findByStationId(String stationId);
    String update(ChargingStationDomain domain);
    String updateEss(ChargingStationDomain domain);
    String findStationIdByStationName(String stationName);
//    List<String> findStationNames(String cursor, int size);
}
