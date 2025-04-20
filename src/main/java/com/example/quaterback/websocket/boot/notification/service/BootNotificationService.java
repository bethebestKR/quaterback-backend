package com.example.quaterback.websocket.boot.notification.service;

import com.example.quaterback.station.repository.ChargingStationRepository;
import com.example.quaterback.websocket.boot.notification.converter.BootNotificationConverter;
import com.example.quaterback.websocket.boot.notification.domain.BootNotificationDomain;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BootNotificationService {

    private final ChargingStationRepository chargingStationRepository;
    private final BootNotificationConverter converter;


    public String stationPowerUp(JsonNode jsonNode) {
        BootNotificationDomain domain = converter.convertToBootNotificationDomain(jsonNode);
        String stationId = chargingStationRepository.updateStationStatus(domain.extractStationId());
        return stationId;
    }
}
