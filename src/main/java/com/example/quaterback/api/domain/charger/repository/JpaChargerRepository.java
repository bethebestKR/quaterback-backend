package com.example.quaterback.api.domain.charger.repository;

import com.example.quaterback.api.domain.charger.entity.ChargerEntity;
import com.example.quaterback.api.domain.charger.domain.ChargerDomain;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<ChargerDomain> findByStationID(String stationId) {
        List<ChargerEntity> chargerEntities = chargerRepository.findByStation_StationId(stationId);
        List<ChargerDomain> chargerDomains = chargerEntities.stream()
                .map(ChargerDomain :: fromEntityToDomain)
                .collect(Collectors.toList());
        return chargerDomains;
    }
}
