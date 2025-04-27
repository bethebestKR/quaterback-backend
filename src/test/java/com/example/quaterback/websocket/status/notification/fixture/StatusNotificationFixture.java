package com.example.quaterback.websocket.status.notification.fixture;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.time.LocalDateTime;

public class StatusNotificationFixture {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static JsonNode createStatusNotificationJsonNode(
            int messageId,
            String messageType,
            String action,
            String timestamp,
            String connectorStatus,
            Integer evseId,
            Integer connectorId,
            String vendorId,
            String stationId
    ) {
        ArrayNode rootArray = mapper.createArrayNode();

        rootArray.add(messageId);
        rootArray.add(messageType);
        rootArray.add(action);

        ObjectNode payload = mapper.createObjectNode();
        payload.put("timestamp", timestamp.toString());
        payload.put("connectorStatus", connectorStatus);
        payload.put("evseId", evseId);
        payload.put("connectorId", connectorId);

        ObjectNode customData = mapper.createObjectNode();
        customData.put("vendorId", vendorId);
        customData.put("stationId", stationId);

        payload.set("customData", customData);

        rootArray.add(payload);

        return rootArray;
    }

}
