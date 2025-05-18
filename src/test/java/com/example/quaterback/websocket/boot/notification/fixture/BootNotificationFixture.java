package com.example.quaterback.websocket.boot.notification.fixture;

import com.example.quaterback.api.domain.station.constant.StationStatus;
import com.example.quaterback.api.domain.station.entity.ChargingStationEntity;
import com.example.quaterback.websocket.boot.notification.domain.BootNotificationDomain;
import com.example.quaterback.websocket.boot.notification.domain.sub.BootCustomData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.time.LocalDateTime;

public class BootNotificationFixture {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static JsonNode createBootNotificationJsonNode(
            Integer messageType,
            String messageId,
            String action,
            String reason,
            String model,
            String vendorName,
            String vendorId,
            String stationId
    ) {
        ArrayNode rootArray = mapper.createArrayNode();

        rootArray.add(messageType);
        rootArray.add(messageId);
        rootArray.add(action);

        // Payload 객체 생성
        ObjectNode payload = mapper.createObjectNode();
        payload.put("reason", reason);

        ObjectNode chargingStation = mapper.createObjectNode();
        chargingStation.put("model", model);
        chargingStation.put("vendorName", vendorName);
        payload.set("chargingStation", chargingStation);

        ObjectNode customData = mapper.createObjectNode();
        customData.put("vendorId", vendorId);
        customData.put("stationId", stationId);

        payload.set("customData", customData);

        rootArray.add(payload);

        return rootArray;
    }

    public static ChargingStationEntity createInitialChargingStationEntity(
            String model,
            String vendorId,
            String stationId,
            Double latitude,
            Double longitude,
            String address,
            StationStatus status,
            LocalDateTime updateDateTime,
            Double essValue
    ) {
        return ChargingStationEntity.builder()
                .stationId(stationId)
                .model(model)
                .vendorId(vendorId)
                .latitude(latitude)
                .longitude(longitude)
                .address(address)
                .stationStatus(status)
                .updateStatusTimeStamp(updateDateTime)
                .essValue(essValue)
                .build();
    }

    public static BootNotificationDomain createExpectedDomain(
            String messageType,
            String messageId,
            String action,
            String reason,
            String model,
            String vendorId,
            String stationId
    ) {
        BootNotificationDomain expected = new BootNotificationDomain(
                messageType,
                messageId,
                action,
                reason,
                model,
                new BootCustomData(
                        vendorId,
                        stationId)
        );
        return expected;
    }
}
