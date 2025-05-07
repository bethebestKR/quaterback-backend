package com.example.quaterback.api.domain.station.repository;

import com.example.quaterback.api.domain.station.entity.ChargingStationEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SpringDataJpaChargingStationRepository extends JpaRepository<ChargingStationEntity, Long> {
    Optional<ChargingStationEntity> findByStationId(String stationId);

    Optional<ChargingStationEntity> findByStationName(String stationName);
}
