package com.example.quaterback.websocket.boot.notification.service;

import com.example.quaterback.station.entity.ChargingStationEntity;
import com.example.quaterback.station.repository.ChargingStationRepository;
import com.example.quaterback.websocket.boot.notification.converter.BootNotificationConverter;
import com.example.quaterback.websocket.boot.notification.domain.BootNotificationDomain;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BootNotificationService {

    private final ChargingStationRepository chargingStationRepository;
    private final BootNotificationConverter converter;

    @Transactional
    public String stationPowerUp(JsonNode jsonNode) {
        BootNotificationDomain domain = converter.convertToBootNotificationDomain(jsonNode);
        ChargingStationEntity entity = chargingStationRepository.findByStationId(domain.extractStationId());

        if (entity.getStationStatus().equals("inactive")) {
            entity.setStationStatus("active");
            entity.setUpdateStatusTimeStamp(LocalDateTime.now());
        }

        return entity.getStationId();
    }
}
