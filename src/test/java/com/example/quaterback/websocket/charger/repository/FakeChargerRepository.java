package com.example.quaterback.websocket.charger.repository;

import com.example.quaterback.charger.entity.ChargerEntity;
import com.example.quaterback.charger.repository.ChargerRepository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FakeChargerRepository implements ChargerRepository {

    private final Map<String, ChargerEntity> storage = new ConcurrentHashMap<>();

    @Override
    public Integer updateChargerStatus(String stationId, Integer evseId, String status) {
        ChargerEntity entity = findByStation_StationIdAndEvseId(stationId, evseId);
        entity.setChargerStatus(status);
        entity.setUpdateStatusTimeStamp(LocalDateTime.now());
        storage.put(stationId + evseId, entity);
        return entity.getEvseId();
    }

    public void initializeStorage(ChargerEntity entity) {
        String key = entity.getStation().getStationId() + entity.getEvseId();
        storage.put(key, entity);
    }

    public ChargerEntity findByStation_StationIdAndEvseId(String stationId, Integer evseId) {
        return storage.get(stationId + evseId);
    }
}
