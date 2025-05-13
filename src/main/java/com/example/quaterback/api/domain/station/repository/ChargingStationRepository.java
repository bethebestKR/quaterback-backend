package com.example.quaterback.api.domain.station.repository;

import com.example.quaterback.api.domain.station.domain.ChargingStationDomain;

import java.util.List;
import com.example.quaterback.api.feature.dashboard.dto.query.StationFullInfoQuery;

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

    void save(ChargingStationDomain chargingStationDomain);

}
