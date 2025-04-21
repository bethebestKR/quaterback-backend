package com.example.quaterback.websocket.boot.notification.repository;

import com.example.quaterback.station.entity.ChargingStationEntity;
import com.example.quaterback.station.repository.ChargingStationRepository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FakeChargingStationRepository implements ChargingStationRepository {

    private final Map<String, ChargingStationEntity> storage = new ConcurrentHashMap<>();

    @Override
    public ChargingStationEntity findByStationId(String stationId) {
        return storage.get(stationId);
    }

    public void initializeStorage(ChargingStationEntity entity) {
        storage.put(entity.getStationId(), entity);
    }
}
