package com.example.quaterback.station.repository;

import com.example.quaterback.station.entity.ChargingStationEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaChargingStationRepository implements ChargingStationRepository {

    private final SpringDataJpaChargingStationRepository chargingStationRepository;

    @Override
    public ChargingStationEntity findByStationId(String stationId) {
        ChargingStationEntity entity = chargingStationRepository.findByStationId(stationId);

        if (entity == null)
            throw new EntityNotFoundException("entity not found");

        return entity;
    }

}
