package com.example.quaterback.websocket.boot.notification.service;

import com.example.quaterback.api.domain.charger.repository.FakeChargerRepository;
import com.example.quaterback.api.domain.station.constant.StationStatus;
import com.example.quaterback.api.domain.station.domain.ChargingStationDomain;
import com.example.quaterback.api.domain.station.entity.ChargingStationEntity;
import com.example.quaterback.api.domain.station.repository.FakeChargingStationRepository;
import com.example.quaterback.common.redis.service.RedisMapSessionToStationService;
import com.example.quaterback.websocket.boot.notification.converter.BootNotificationConverter;
import com.example.quaterback.websocket.boot.notification.fixture.BootNotificationFixture;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class BootNotificationServiceTest {

    @Mock
    private RedisMapSessionToStationService redisMappingService;

    private BootNotificationService bootNotificationService;

    private BootNotificationConverter converter;
    private FakeChargingStationRepository repository;
    private FakeChargerRepository chargerRepository;

    @BeforeEach
    void setUp() {
        converter = new BootNotificationConverter();
        repository = new FakeChargingStationRepository();
        chargerRepository = new FakeChargerRepository();
        bootNotificationService = new BootNotificationService(repository, converter, redisMappingService, chargerRepository);
    }

    @Test
    void stationPowerUpTest_BootNotification_jsonNode를_받으면_충전소_상태를_갱신하고_충전소_id를_반환한다() {
        //given
        given(redisMappingService.mapSessionToStation(anyString(), anyString())).willReturn("station-001");

        String model = "R1";
        String vendorId = "quarterback";
        String stationId = "station-001";
        Double latitude = 37.5665;
        Double longitude = 126.9780;
        String address = "서울특별시 중구 세종대로 110";
        JsonNode jsonNode = BootNotificationFixture.createBootNotificationJsonNode(
                2,
                "12345678-boot-location",
                "BootNotification",
                "PowerUp",
                model,
                vendorId,
                vendorId,
                stationId
        );

        // 충전소 초기 데이터 세팅 및 초기 값 저장
        ChargingStationEntity entity = BootNotificationFixture.createInitialChargingStationEntity(
                model,
                vendorId,
                stationId,
                latitude,
                longitude,
                address,
                StationStatus.INACTIVE,
                LocalDateTime.of(2025, 2, 10, 8, 30, 20),
                200000
        );
        LocalDateTime before = entity.getUpdateStatusTimeStamp();
        repository.initializeStorage(entity);
        String sessionId = "123";

        //when
        String result = bootNotificationService.updateStationStatus(jsonNode, sessionId);

        //then
        String resultStationId = entity.getStationId();
        assertThat(result).isEqualTo(resultStationId);

        ChargingStationDomain resultDomain = repository.findByStationId(stationId);
        assertThat(resultDomain.getStationStatus()).isEqualTo(StationStatus.ACTIVE);
        assertThat(resultDomain.getUpdateStatusTimeStamp()).isAfter(before);
    }
}