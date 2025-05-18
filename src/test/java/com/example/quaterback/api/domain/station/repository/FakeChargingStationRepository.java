package com.example.quaterback.api.domain.station.repository;

import com.example.quaterback.api.domain.station.constant.StationStatus;
import com.example.quaterback.api.domain.station.domain.ChargingStationDomain;
import com.example.quaterback.api.domain.station.entity.ChargingStationEntity;
import com.example.quaterback.api.feature.dashboard.dto.query.StationFullInfoQuery;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FakeChargingStationRepository implements ChargingStationRepository {

    private final Map<String, ChargingStationEntity> storage = new ConcurrentHashMap<>();

    @Override
    public ChargingStationDomain findByStationId(String stationId) {
        return storage.get(stationId).toDomain();
    }

    @Override
    public String update(ChargingStationDomain domain) {
        ChargingStationEntity entity = storage.get(domain.getStationId());
        entity.updateStationStatus(domain.getStationStatus());
        storage.put(entity.getStationId(), entity);
        return entity.getStationId();
    }

    @Override
    public String updateEss(ChargingStationDomain domain) {
        return "";
    }

    @Override
    public String findStationIdByStationName(String stationName) {
        return "";
    }

    @Override
    public List<StationFullInfoQuery> getFullStationInfos() {
        return List.of();
    }

    @Override
    public StationFullInfoQuery getFullStationInfo(String stationName) {
        return null;
    }

    @Override
    public int deleteByName(String stationName) {
        return 0;
    }

    @Override
    public List<ChargingStationDomain> findAll() {
        return List.of();
    }

    @Override
    public ChargingStationDomain findByStationName(String stationName) {
        return null;
    }

    @Override
    public void save(ChargingStationDomain chargingStationDomain) {

    }

    @Override
    public String updateStationStatus(String stationId, StationStatus status) {
        return "";
    }

    public void initializeStorage(ChargingStationEntity entity) {
        storage.put(entity.getStationId(), entity);
    }
}
