package com.example.quaterback.websocket.status.notification.converter;

import com.example.quaterback.websocket.status.notification.domain.StatusNotificationDomain;
import com.example.quaterback.websocket.status.notification.fixture.StatusNotificationFixture;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class StatusNotificationConverterTest {

    private StatusNotificationConverter converter;

    @BeforeEach
    void setUp() {
        converter = new StatusNotificationConverter();
    }

    @Test
    void convertToStatusNotificationDomain_전달_받은_jsonNode를_Domain으로_변환() throws IllegalAccessException {

        //given
        Integer messageType = 2;
        String messageId = "status-notif-001";
        String action = "StatusNotification";
        LocalDateTime timestamp = LocalDateTime.of(2025,4,20,16,30,00);
        String connectorStatus = "Occupied";
        Integer evseId = 1;
        Integer connectorId = 1;
        String vendorId = "quarterback";
        String stationId = "station-001";
        JsonNode jsonNode = StatusNotificationFixture.createStatusNotificationJsonNode(
                messageType,
                messageId,
                action,
                timestamp.toString(),
                connectorStatus,
                evseId,
                connectorId,
                vendorId,
                stationId
        );
        StatusNotificationDomain expected = StatusNotificationFixture.createExpectedDomain(
                messageType.toString(),
                messageId,
                action,
                timestamp,
                connectorStatus,
                evseId,
                connectorId,
                vendorId,
                stationId
        );

        //when
        StatusNotificationDomain result = converter.convertToStatusNotificationDomain(jsonNode);

        //then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

}
