package com.example.quaterback.api.domain.charger.repository;

import com.example.quaterback.api.domain.charger.domain.ChargerDomain;

public interface ChargerRepository {

    ChargerDomain findByStationIdAndEvseId(String stationId, Integer evseId);
    Integer update(ChargerDomain domain);
}
