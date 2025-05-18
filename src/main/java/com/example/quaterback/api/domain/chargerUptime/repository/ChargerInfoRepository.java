package com.example.quaterback.api.domain.chargerUptime.repository;

import com.example.quaterback.api.domain.chargerUptime.entity.ChargerUptimeEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface ChargerInfoRepository {
    List<ChargerUptimeEntity> getInfosByTimeRange(
            LocalDateTime start, LocalDateTime end);

}
