package com.example.quaterback.api.domain.station.repository;

import com.example.quaterback.api.domain.station.domain.ChargingStationDomain;
import com.example.quaterback.api.domain.station.entity.ChargingStationEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaChargingStationRepository implements ChargingStationRepository {

    private final SpringDataJpaChargingStationRepository chargingStationRepository;

    @Override
    public ChargingStationDomain findByStationId(String stationId) {
        ChargingStationEntity entity = chargingStationRepository.findByStationId(stationId)
                .orElseThrow(() -> new EntityNotFoundException("entity not found"));

        return entity.toDomain();
    }

    public String update(ChargingStationDomain domain) {
        ChargingStationEntity entity = chargingStationRepository.findByStationId(domain.getStationId())
                .orElseThrow(() -> new EntityNotFoundException("entity not found"));

        entity.updateStationStatus(domain.getStationStatus());

        return entity.getStationId();
    }

}
