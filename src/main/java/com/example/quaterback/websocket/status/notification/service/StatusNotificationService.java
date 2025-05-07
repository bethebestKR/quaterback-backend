package com.example.quaterback.websocket.status.notification.service;

import com.example.quaterback.api.domain.charger.constant.ChargerStatus;
import com.example.quaterback.api.domain.charger.domain.ChargerDomain;
import com.example.quaterback.api.domain.charger.repository.ChargerRepository;
import com.example.quaterback.common.redis.service.RedisMapSessionToStationService;
import com.example.quaterback.websocket.status.notification.converter.StatusNotificationConverter;
import com.example.quaterback.websocket.status.notification.domain.StatusNotificationDomain;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StatusNotificationService {

    private final ChargerRepository chargerRepository;
    private final StatusNotificationConverter converter;
    private final RedisMapSessionToStationService redisMappingService;

    @Transactional
    public Integer chargerStatusUpdated(JsonNode jsonNode, String sessionId) {
        StatusNotificationDomain statusNotificationDomain = converter.convertToStatusNotificationDomain(jsonNode);

        String stationId = redisMappingService.getStationId(sessionId);

        ChargerDomain chargerDomain = chargerRepository.findByStationIdAndEvseId(stationId, statusNotificationDomain.getEvseId());
        chargerDomain.updateChargerStatus(ChargerStatus.valueOf(statusNotificationDomain.getConnectorStatus().toUpperCase()));
        Integer evseId = chargerRepository.update(chargerDomain);
        return evseId;
    }
}
