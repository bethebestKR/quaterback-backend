package com.example.quaterback.websocket.boot.notification.service;

import com.example.quaterback.api.domain.station.constant.StationStatus;
import com.example.quaterback.api.domain.station.domain.ChargingStationDomain;
import com.example.quaterback.api.domain.station.repository.ChargingStationRepository;
import com.example.quaterback.common.redis.service.RedisMapSessionToStationService;
import com.example.quaterback.websocket.boot.notification.converter.BootNotificationConverter;
import com.example.quaterback.websocket.boot.notification.domain.BootNotificationDomain;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BootNotificationService {

    private final ChargingStationRepository chargingStationRepository;
    private final BootNotificationConverter converter;
    private final RedisMapSessionToStationService redisMappingService;

    @Transactional
    public String updateStationStatus(JsonNode jsonNode, String sessionId) {
        BootNotificationDomain bootNotificationDomain = converter.convertToBootNotificationDomain(jsonNode);

        String stationId = redisMappingService.mapSessionToStation(sessionId, bootNotificationDomain.extractStationId());
        ChargingStationDomain chargingStationDomain = chargingStationRepository.findByStationId(stationId);

        chargingStationDomain.updateStationStatus(StationStatus.ACTIVE);
        String resultStationId = chargingStationRepository.update(chargingStationDomain);

        return resultStationId;
    }
}
