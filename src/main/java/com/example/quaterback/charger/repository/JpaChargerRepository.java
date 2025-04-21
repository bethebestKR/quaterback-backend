package com.example.quaterback.charger.repository;

import com.example.quaterback.charger.entity.ChargerEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class JpaChargerRepository implements ChargerRepository {

    private final SpringDataJpaChargerRepository chargerRepository;

    @Transactional
    public Integer updateChargerStatus(String stationId, Integer evseId, String status) {
        ChargerEntity entity = chargerRepository.findByStation_StationIdAndEvseId(stationId, evseId);

        if (entity == null)
            throw new EntityNotFoundException("entity not found");

        if (!entity.getChargerStatus().equals(status)) {
            entity.setChargerStatus(status);
            entity.setUpdateStatusTimeStamp(LocalDateTime.now());
        }

        return entity.getEvseId();
    }
}
