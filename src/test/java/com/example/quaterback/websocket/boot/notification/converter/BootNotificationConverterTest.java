package com.example.quaterback.websocket.boot.notification.converter;

import com.example.quaterback.websocket.boot.notification.domain.BootNotificationDomain;
import com.example.quaterback.websocket.boot.notification.fixture.BootNotificationFixture;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BootNotificationConverterTest {

    private BootNotificationConverter converter;

    @BeforeEach
    void setUp() {
        converter = new BootNotificationConverter();
    }

    @Test
    void convertToBootNotificationDomain_전달_받은_jsonNode를_BootNotificationDomain으로_변환() {

        //given
        Integer messageType = 2;
        String messageId = "12345678-boot-location";
        String action = "BootNotification";
        String reason = "PowerUp";
        String model = "R1";
        String vendorName = "quarterback";
        String vendorId = "quarterback";
        String stationId = "station-001";
        JsonNode jsonNode = BootNotificationFixture.createBootNotificationJsonNode(
                messageType,
                messageId,
                action,
                reason,
                model,
                vendorName,
                vendorId,
                stationId
        );
        BootNotificationDomain expected = BootNotificationFixture.createExpectedDomain(
                messageType.toString(),
                messageId,
                action,
                reason,
                model,
                vendorId,
                stationId
        );

        //when
        BootNotificationDomain result = converter.convertToBootNotificationDomain(jsonNode);

        //then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}