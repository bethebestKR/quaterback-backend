package com.example.quaterback.api.domain.station.repository;

import com.example.quaterback.api.domain.station.entity.ChargingStationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaChargingStationRepository extends JpaRepository<ChargingStationEntity, Long> {
    Optional<ChargingStationEntity> findByStationId(String stationId);
}
