package com.example.quaterback.websocket.boot.notification.service;

import com.example.quaterback.station.entity.ChargingStationEntity;
import com.example.quaterback.websocket.boot.notification.converter.BootNotificationConverter;
import com.example.quaterback.websocket.boot.notification.domain.BootNotificationDomain;
import com.example.quaterback.websocket.boot.notification.repository.FakeChargingStationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class BootNotificationServiceTest {

    private BootNotificationService bootNotificationService;
    private BootNotificationConverter converter;
    private FakeChargingStationRepository repository;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        converter = new BootNotificationConverter();
        repository = new FakeChargingStationRepository();
        bootNotificationService = new BootNotificationService(repository, converter);
    }

    @DisplayName("stationPowerUp - jsonNode를 domain으로 convert 후 db의 충전소 상태를 갱신하는 메소드 호출 후 id 반환")
    @Test
    void stationPowerUpSuccess() throws JsonProcessingException, InterruptedException {
        //given
        String json = """
                [
                  2,
                  "12345678-boot-location",
                  "BootNotification",
                  {
                    "reason": "PowerUp",
                    "chargingStation": {
                      "model": "R1",
                      "vendorName": "quarterback"
                    },
                    "customData": {
                      "vendorId": "quarterback",
                      "stationId": "station-001",
                      "location": {
                        "latitude": 37.5665,
                        "longitude": 126.9780,
                        "address": "서울특별시 중구 세종대로 110"
                      }
                    }
                  }
                ]
                """;
        JsonNode jsonNode = objectMapper.readTree(json);

        // 충전소 초기 데이터 세팅 및 초기 값 저장
        BootNotificationDomain domain = converter.convertToBootNotificationDomain(jsonNode);
        ChargingStationEntity entity = ChargingStationEntity.from(domain);
        entity.setUpdateStatusTimeStamp(LocalDateTime.of(2025, 2, 10, 8, 30, 20));
        entity.setStationStatus("inactive");
        LocalDateTime before = entity.getUpdateStatusTimeStamp();
        repository.initializeStorage(entity);

        //when
        String result = bootNotificationService.stationPowerUp(jsonNode);

        //then
        String stationId = entity.getStationId();
        assertThat(result).isEqualTo(stationId);

        ChargingStationEntity resultEntity = repository.findByStationId(stationId);
        assertThat(resultEntity.getStationStatus()).isEqualTo("active");
        assertThat(resultEntity.getUpdateStatusTimeStamp()).isAfter(before);
    }
}