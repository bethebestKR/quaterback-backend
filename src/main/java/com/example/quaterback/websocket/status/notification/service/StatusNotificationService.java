package com.example.quaterback.websocket.status.notification.service;

import com.example.quaterback.charger.repository.ChargerRepository;
import com.example.quaterback.websocket.status.notification.converter.StatusNotificationConverter;
import com.example.quaterback.websocket.status.notification.domain.StatusNotificationDomain;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatusNotificationService {

    private final ChargerRepository chargerRepository;
    private final StatusNotificationConverter converter;

    public Integer chargerStatusUpdated(JsonNode jsonNode) {
        StatusNotificationDomain domain = converter.convertToStatusNotificationDomain(jsonNode);
        Integer evseId = chargerRepository.updateChargerStatus(domain.extractStationId(), domain.getEvseId(), domain.getConnectorStatus());
        return evseId;
    }
}
