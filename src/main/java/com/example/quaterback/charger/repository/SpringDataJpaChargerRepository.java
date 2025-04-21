package com.example.quaterback.charger.repository;

import com.example.quaterback.charger.entity.ChargerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaChargerRepository extends JpaRepository<ChargerEntity, Long> {
    ChargerEntity findByStation_StationIdAndEvseId(String stationId, Integer evseId);
}
