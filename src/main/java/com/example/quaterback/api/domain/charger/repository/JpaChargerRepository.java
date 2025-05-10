package com.example.quaterback.api.domain.charger.repository;

import com.example.quaterback.api.domain.charger.entity.ChargerEntity;
import com.example.quaterback.api.domain.charger.domain.ChargerDomain;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaChargerRepository implements ChargerRepository {

    private final SpringDataJpaChargerRepository chargerRepository;
    @Override
    public ChargerDomain findByStationIdAndEvseId(String stationId, Integer evseId) {
        ChargerEntity entity = chargerRepository.findByStation_StationIdAndEvseId(stationId, evseId)
                .orElseThrow(() -> new EntityNotFoundException("entity not found"));

        return entity.toDomain();
    }

    @Override
    public Integer update(ChargerDomain domain) {
        ChargerEntity entity = chargerRepository.findByStation_StationIdAndEvseId(domain.getStationId(), domain.getEvseId())
                .orElseThrow(() -> new EntityNotFoundException("entity not found"));

        entity.updateChargerStatus(domain.getChargerStatus());

        return entity.getEvseId();
    }

    @Override
    public List<ChargerDomain> findAllByStationId(String stationId) {
        List<ChargerEntity> entityList = chargerRepository.findAllByStationId(stationId);

        return entityList.stream()
                .map(ChargerEntity::toDomain)
                .toList();
    }
}
