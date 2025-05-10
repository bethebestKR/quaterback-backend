package com.example.quaterback.api.domain.station.repository;

import com.example.quaterback.api.domain.station.domain.ChargingStationDomain;
import com.example.quaterback.api.domain.station.entity.ChargingStationEntity;
import com.example.quaterback.api.domain.station.repository.ChargingStationRepository;

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

    public void initializeStorage(ChargingStationEntity entity) {
        storage.put(entity.getStationId(), entity);
    }
}
