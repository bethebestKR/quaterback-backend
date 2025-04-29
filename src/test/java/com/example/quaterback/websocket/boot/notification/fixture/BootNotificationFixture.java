package com.example.quaterback.websocket.boot.notification.fixture;

import com.example.quaterback.station.constant.StationStatus;
import com.example.quaterback.station.entity.ChargingStationEntity;
import com.example.quaterback.websocket.boot.notification.converter.BootNotificationConverter;
import com.example.quaterback.websocket.boot.notification.domain.BootNotificationDomain;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.time.LocalDateTime;

public class BootNotificationFixture {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static JsonNode createBootNotificationJsonNode(
            int messageId,
            String messageType,
            String action,
            String reason,
            String model,
            String vendorName,
            String vendorId,
            String stationId,
            double latitude,
            double longitude,
            String address
    ) {
        ArrayNode rootArray = mapper.createArrayNode();

        rootArray.add(messageId);
        rootArray.add(messageType);
        rootArray.add(action);

        // Payload 객체 생성
        ObjectNode payload = mapper.createObjectNode();
        payload.put("reason", reason);

        ObjectNode chargingStation = mapper.createObjectNode();
        chargingStation.put("model", model);
        chargingStation.put("vendorName", vendorName);
        payload.set("chargingStation", chargingStation);

        ObjectNode location = mapper.createObjectNode();
        location.put("latitude", latitude);
        location.put("longitude", longitude);
        location.put("address", address);

        ObjectNode customData = mapper.createObjectNode();
        customData.put("vendorId", vendorId);
        customData.put("stationId", stationId);
        customData.set("location", location);

        payload.set("customData", customData);

        rootArray.add(payload);

        return rootArray;
    }

    public static ChargingStationEntity createInitialChargingStationEntityFromJsonNode(
            JsonNode jsonNode,
            StationStatus status,
            LocalDateTime updateDateTime
    ) {
        BootNotificationConverter converter = new BootNotificationConverter();
        BootNotificationDomain domain = converter.convertToBootNotificationDomain(jsonNode);
        ChargingStationEntity entity = ChargingStationEntity.builder()
                .stationId(domain.extractStationId())
                .model(domain.getModel())
                .vendorId(domain.extractVendorId())
                .latitude(domain.extractLatitude())
                .longitude(domain.extractLongitude())
                .address(domain.extractAddress())
                .updateStatusTimeStamp(updateDateTime)
                .stationStatus(status)
                .build();
        return entity;
    }
}
