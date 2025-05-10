package com.example.quaterback.api.domain.station.repository;

import com.example.quaterback.api.domain.station.domain.ChargingStationDomain;
import com.example.quaterback.api.domain.station.entity.ChargingStationEntity;

import java.util.List;
import com.example.quaterback.api.feature.dashboard.dto.query.StationFullInfoQuery;

import java.util.List;

public interface ChargingStationRepository {
    ChargingStationDomain findByStationId(String stationId);
    String update(ChargingStationDomain domain);
    String updateEss(ChargingStationDomain domain);
    String findStationIdByStationName(String stationName);
//    List<String> findStationNames(String cursor, int size);
    List<StationFullInfoQuery> getFullStationInfos();
    StationFullInfoQuery getFullStationInfo(String stationName);

    int deleteByName(String stationName);

    List<ChargingStationDomain> findAll();

    ChargingStationDomain findByStationName(String stationName);
}
