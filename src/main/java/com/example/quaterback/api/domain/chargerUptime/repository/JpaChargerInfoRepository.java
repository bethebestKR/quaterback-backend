package com.example.quaterback.api.domain.chargerUptime.repository;

import com.example.quaterback.api.domain.chargerUptime.entity.ChargerUptimeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaChargerInfoRepository implements ChargerInfoRepository{
    private final SpringDataJpaChargerInfoRepository chargerInfoRepository;
    @Override
    public List<ChargerUptimeEntity> getInfosByTimeRange(LocalDateTime start, LocalDateTime end) {
        List<ChargerUptimeEntity> chargerInfoEntities = chargerInfoRepository.findByTimeRange(
                start, end);

        return chargerInfoEntities;
    }
}
