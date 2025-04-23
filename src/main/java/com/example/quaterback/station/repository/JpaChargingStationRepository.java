package com.example.quaterback.station.repository;

import com.example.quaterback.station.domain.ChargingStationDomain;
import com.example.quaterback.station.entity.ChargingStationEntity;
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

        ChargingStationDomain domain = entity.toDomain();
        return domain;
    }

    public String update(ChargingStationDomain domain) {
        ChargingStationEntity entity = chargingStationRepository.findByStationId(domain.getStationId())
                .orElseThrow(() -> new EntityNotFoundException("entity not found"));

        entity.setStationStatus(domain.getStationStatus());
        entity.setUpdateStatusTimeStamp(domain.getUpdateStatusTimeStamp());

        return entity.getStationId();
    }

}
