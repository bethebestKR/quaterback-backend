package com.example.quaterback.websocket.boot.notification.repository;

import com.example.quaterback.station.domain.ChargingStationDomain;
import com.example.quaterback.station.entity.ChargingStationEntity;
import com.example.quaterback.station.repository.ChargingStationRepository;

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
        ChargingStationEntity entity = ChargingStationEntity.from(domain);
        storage.put(entity.getStationId(), entity);
        return entity.getStationId();
    }

    public void initializeStorage(ChargingStationEntity entity) {
        storage.put(entity.getStationId(), entity);
    }
}
