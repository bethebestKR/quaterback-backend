package com.example.quaterback.websocket.boot.notification.repository;

import com.example.quaterback.station.entity.ChargingStationEntity;
import com.example.quaterback.station.repository.ChargingStationRepository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FakeChargingStationRepository implements ChargingStationRepository {

    private final Map<String, ChargingStationEntity> storage = new ConcurrentHashMap<>();

    @Override
    public String updateStationStatus(String stationId) {
        ChargingStationEntity entity = storage.get(stationId);
        entity.setStationStatus("active");
        entity.setUpdateStatusTimeStamp(LocalDateTime.now());
        storage.put(stationId, entity);
        return stationId;
    }

    public void initializeStorage(ChargingStationEntity entity) {
        storage.put(entity.getStationId(), entity);
    }

    public ChargingStationEntity findByStationId(String stationId) {
        return storage.get(stationId);
    }

}
