package com.example.quaterback.websocket.status.notification.fixture;

import com.example.quaterback.websocket.status.notification.domain.StatusNotificationDomain;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.time.LocalDateTime;


public class StatusNotificationFixture {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static JsonNode createStatusNotificationJsonNode(
            Integer messageType,
            String messageId,
            String action,
            String timestamp,
            String connectorStatus,
            Integer evseId,
            Integer connectorId
    ) {
        ArrayNode rootArray = mapper.createArrayNode();

        rootArray.add(messageType);
        rootArray.add(messageId);
        rootArray.add(action);

        ObjectNode payload = mapper.createObjectNode();
        payload.put("timestamp", timestamp.toString());
        payload.put("connectorStatus", connectorStatus);
        payload.put("evseId", evseId);
        payload.put("connectorId", connectorId);

        rootArray.add(payload);

        return rootArray;
    }

    public static StatusNotificationDomain createExpectedDomain(
            String messageType,
            String messageId,
            String action,
            LocalDateTime timestamp,
            String connectorStatus,
            Integer evseId,
            Integer connectorId
    ) {
        StatusNotificationDomain expected = new StatusNotificationDomain(
                messageType,
                messageId,
                action,
                timestamp,
                connectorStatus,
                evseId,
                connectorId
        );
        return expected;
    }

}
