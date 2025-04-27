package com.example.quaterback.websocket.status.notification.converter;

import com.example.quaterback.websocket.status.notification.domain.StatusNotificationDomain;
import com.example.quaterback.websocket.status.notification.fixture.StatusNotificationFixture;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class StatusNotificationConverterTest {

    private StatusNotificationConverter converter;

    @BeforeEach
    void setUp() {
        converter = new StatusNotificationConverter();
    }

    @Test
    void convertToStatusNotificationDomain_전달_받은_jsonNode를_Domain으로_변환() {

        //given
        JsonNode jsonNode = StatusNotificationFixture.createStatusNotificationJsonNode(
                2,
                "status-notif-001",
                "StatusNotification",
                "2025-04-20T16:30:00",
                "Occupied",
                1,
                1,
                "quarterback",
                "station-001"
        );

        //when
        StatusNotificationDomain result = converter.convertToStatusNotificationDomain(jsonNode);

        //then
        assertThat(result.getMessageTypeId()).isEqualTo("2");
        assertThat(result.getMessageId()).isEqualTo("status-notif-001");
        assertThat(result.getAction()).isEqualTo("StatusNotification");
        assertThat(result.getTimeStamp()).isEqualTo("2025-04-20T16:30:00");
        assertThat(result.getConnectorStatus()).isEqualTo("Occupied");
        assertThat(result.getEvseId()).isEqualTo(1);
        assertThat(result.getConnectorId()).isEqualTo(1);
        assertThat(result.extractVendorId()).isEqualTo("quarterback");
        assertThat(result.extractStationId()).isEqualTo("station-001");
    }
}