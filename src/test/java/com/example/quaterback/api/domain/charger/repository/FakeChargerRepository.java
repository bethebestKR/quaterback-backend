package com.example.quaterback.api.domain.charger.repository;

import com.example.quaterback.api.domain.charger.constant.ChargerStatus;
import com.example.quaterback.api.domain.charger.domain.ChargerDomain;
import com.example.quaterback.api.domain.charger.entity.ChargerEntity;
import com.example.quaterback.api.domain.charger.fixture.ChargerFixture;
import com.example.quaterback.api.domain.station.entity.ChargingStationEntity;
import com.example.quaterback.api.domain.station.fixture.ChargingStationFixture;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FakeChargerRepository implements ChargerRepository {

    private final Map<String, ChargerEntity> storage = new ConcurrentHashMap<>();

    @Override
    public ChargerDomain findByStationIdAndEvseId(String stationId, Integer evseId) {
        return storage.get(stationId + evseId).toDomain();
    }

    @Override
    public Integer update(ChargerDomain domain) {
        ChargerEntity entity = storage.get(domain.getStationId() + domain.getEvseId());
        entity.updateChargerStatus(domain.getChargerStatus());
        storage.put(domain.getStationId() + domain.getEvseId(), entity);
        return entity.getEvseId();
    }

    public void initializeStorage(ChargerStatus status, LocalDateTime dateTime) {
        ChargingStationEntity stationEntity = ChargingStationFixture.createStationEntity();
        ChargerEntity chargerEntity = ChargerFixture.createChargerEntity(stationEntity, status, dateTime);
        storage.put(chargerEntity.getStation().getStationId() + chargerEntity.getEvseId(), chargerEntity);
    }
}
