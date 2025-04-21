package com.example.quaterback.charger.repository;

public interface ChargerRepository {
    Integer updateChargerStatus(String stationId, Integer evseId, String status);
}
