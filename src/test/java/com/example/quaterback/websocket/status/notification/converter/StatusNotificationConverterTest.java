package com.example.quaterback.websocket.status.notification.converter;

import com.example.quaterback.websocket.status.notification.domain.StatusNotificationDomain;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class StatusNotificationConverterTest {

    private ObjectMapper objectMapper;
    private StatusNotificationConverter converter;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        converter = new StatusNotificationConverter();
    }

    @DisplayName("convertToStatusNotificationDomain - 전달 받은 jsonNode를 StatusNotificationDomain으로 변환")
    @Test
    void convertToStatusNotificationDomainSuccess() throws JsonProcessingException {

        //given
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