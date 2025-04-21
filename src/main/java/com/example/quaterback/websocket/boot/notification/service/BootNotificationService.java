package com.example.quaterback.websocket.boot.notification.service;

import com.example.quaterback.station.constant.StationStatus;
import com.example.quaterback.station.domain.ChargingStationDomain;
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

    public String updateStationStatus(JsonNode jsonNode) {
        BootNotificationDomain bootNotificationDomain = converter.convertToBootNotificationDomain(jsonNode);

        ChargingStationDomain chargingStationDomain = chargingStationRepository.findByStationId(bootNotificationDomain.extractStationId());

        String stationId = chargingStationDomain.getStationId();
        if (chargingStationDomain.getStationStatus().equals(StationStatus.INACTIVE)) {
            chargingStationDomain.updateStationStatus(StationStatus.ACTIVE);
            stationId = chargingStationRepository.save(chargingStationDomain);
        }

        return stationId;
    }
}
