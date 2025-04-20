package com.example.quaterback.websocket.boot.notification.converter;

import com.example.quaterback.websocket.boot.notification.domain.BootNotificationDomain;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BootNotificationConverterTest {

    private ObjectMapper objectMapper;
    private BootNotificationConverter converter;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        converter = new BootNotificationConverter();
    }

    @DisplayName("convertToBootNotificationDomain - 전달 받은 jsonNode를 BootNotificationDomain으로 변환")
    @Test
    void convertToBootNotificationDomainSuccess() throws JsonProcessingException {

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

        //when
        BootNotificationDomain result = converter.convertToBootNotificationDomain(jsonNode);

        //then
        assertThat(result.getMessageTypeId()).isEqualTo("2");
        assertThat(result.getMessageId()).isEqualTo("12345678-boot-location");
        assertThat(result.getAction()).isEqualTo("BootNotification");
        assertThat(result.getReason()).isEqualTo("PowerUp");
        assertThat(result.getModel()).isEqualTo("R1");

        assertThat(result.extractVendorId()).isEqualTo("quarterback");
        assertThat(result.extractStationId()).isEqualTo("station-001");
        assertThat(result.extractLatitude()).isEqualTo(37.5665);
        assertThat(result.extractLongitude()).isEqualTo(126.9780);
        assertThat(result.extractAddress()).isEqualTo("서울특별시 중구 세종대로 110");
    }
}