package com.example.quaterback.charger.repository;

import com.example.quaterback.charger.domain.ChargerDomain;

public interface ChargerRepository {

    ChargerDomain findByStationIdAndEvseId(String stationId, Integer evseId);
    Integer update(ChargerDomain domain);
}
