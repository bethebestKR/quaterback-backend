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
        JsonNode jsonNode = BootNotificationFixture.createBootNotificationJsonNode(
                2,
                "12345678-boot-location",
                "BootNotification",
                "PowerUp",
                "R1",
                "quarterback",
                "quarterback",
                "station-001",
                37.5665,
                126.9780,
                "서울특별시 중구 세종대로 110"
        );

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