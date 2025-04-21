package com.example.quaterback.websocket.status.notification.service;

import com.example.quaterback.charger.entity.ChargerEntity;
import com.example.quaterback.station.entity.ChargingStationEntity;
import com.example.quaterback.websocket.charger.repository.FakeChargerRepository;
import com.example.quaterback.websocket.station.repository.FakeChargingStationRepository;
import com.example.quaterback.websocket.status.notification.converter.StatusNotificationConverter;
import com.example.quaterback.websocket.status.notification.domain.StatusNotificationDomain;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class StatusNotificationServiceTest {

    private StatusNotificationService statusNotificationService;
    private StatusNotificationConverter converter;
    private FakeChargerRepository chargerRepository;
    private FakeChargingStationRepository chargingStationRepository;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        converter = new StatusNotificationConverter();
        chargerRepository = new FakeChargerRepository();
        chargingStationRepository = new FakeChargingStationRepository();
        statusNotificationService = new StatusNotificationService(chargerRepository, converter);
    }

    @Test
    void chargerStatusUpdatedSuccess() throws JsonProcessingException {

        //given
        initChargingStation();
        String json = """
                [
                  2,
                  "status-notif-001",
                  "StatusNotification",
                  {
                    "timestamp": "2025-04-20T16:30:00",
                    "connectorStatus": "Occupied",
                    "evseId": 1,
                    "connectorId": 1,
                    "customData": {
                      "vendorId": "quarterback",
                      "stationId": "station-001"
                    }
                  }
                ]
                """;
        JsonNode jsonNode = objectMapper.readTree(json);

        StatusNotificationDomain domain = converter.convertToStatusNotificationDomain(jsonNode);
        ChargerEntity entity = createEntityFromDomain(domain);
        LocalDateTime before = entity.getUpdateStatusTimeStamp();
        chargerRepository.initializeStorage(entity);

        //when
        Integer result = statusNotificationService.chargerStatusUpdated(jsonNode);

        //then
        Integer evseId = entity.getEvseId();
        assertThat(result).isEqualTo(evseId);

        ChargerEntity resultEntity = chargerRepository.findByStation_StationIdAndEvseId(entity.getStation().getStationId(), entity.getEvseId());
        assertThat(resultEntity.getChargerStatus()).isEqualTo("Occupied");
        assertThat(resultEntity.getUpdateStatusTimeStamp()).isAfter(before);
    }

    private ChargerEntity createEntityFromDomain(StatusNotificationDomain domain) {
        return ChargerEntity.builder()
                .evseId(domain.getEvseId())
                .chargerStatus(domain.getConnectorStatus())
                .station(chargingStationRepository.findByStationId(domain.extractStationId()))
                .updateStatusTimeStamp(LocalDateTime.of(2025,3,10,12,12,12))
                .build();
    }

    private void initChargingStation() {
        ChargingStationEntity entity = ChargingStationEntity.builder()
                .stationId("station-001")
                .model("model")
                .vendorId("quarterback")
                .latitude(37.556)
                .longitude(126.123)
                .address("서울 세종")
                .updateStatusTimeStamp(LocalDateTime.of(2025,3,10,12,30,10))
                .stationStatus("active")
                .build();
        chargingStationRepository.initializeStorage(entity);
    }


}