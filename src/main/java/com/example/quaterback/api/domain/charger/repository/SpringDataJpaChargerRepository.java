package com.example.quaterback.api.domain.charger.repository;

import com.example.quaterback.api.domain.charger.entity.ChargerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataJpaChargerRepository extends JpaRepository<ChargerEntity, Long> {
    Optional<ChargerEntity> findByStation_StationIdAndEvseId(String stationId, Integer evseId);
    List<ChargerEntity> findByStation_StationId(String stationId);
}
