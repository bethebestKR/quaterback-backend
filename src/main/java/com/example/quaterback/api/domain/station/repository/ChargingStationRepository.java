package com.example.quaterback.api.domain.station.repository;

import com.example.quaterback.api.domain.station.domain.ChargingStationDomain;
import com.example.quaterback.api.feature.dashboard.dto.query.StationFullInfoQuery;

import java.util.List;

public interface ChargingStationRepository {
    ChargingStationDomain findByStationId(String stationId);
    String update(ChargingStationDomain domain);
    String updateEss(ChargingStationDomain domain);
    List<StationFullInfoQuery> getFullStationInfos();
    StationFullInfoQuery getFullStationInfo(String stationName);

    int deleteByName(String stationName);

    List<ChargingStationDomain> findAll();
}
