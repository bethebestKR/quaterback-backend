package com.example.quaterback.station.repository;

import com.example.quaterback.station.entity.ChargingStationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaChargingStationRepository extends JpaRepository<ChargingStationEntity, Long> {
    ChargingStationEntity findByStationId(String stationId);
}
