package com.example.quaterback.station.repository;

import com.example.quaterback.station.entity.ChargingStationEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class JpaChargingStationRepository implements ChargingStationRepository {

    private final SpringDataJpaChargingStationRepository chargingStationRepository;

    @Transactional
    public String updateStationStatus(String stationId) {
        ChargingStationEntity entity = chargingStationRepository.findByStationId(stationId);

        if (entity == null)
            throw new EntityNotFoundException("entity not found");

        entity.setStationStatus("active");
        entity.setUpdateStatusTimeStamp(LocalDateTime.now());
        return entity.getStationId();
    }

}
