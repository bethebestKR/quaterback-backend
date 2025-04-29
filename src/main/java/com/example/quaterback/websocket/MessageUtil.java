package com.example.quaterback.websocket;

import com.fasterxml.jackson.databind.JsonNode;

public class MessageUtil {

    public static boolean isValidOcppCallMessage(JsonNode node) {
        return node != null && node.isArray() && node.size() >= 4 && node.get(0).asInt() == 2;
    }

    public static String getMessageTypeId(JsonNode node) {
        return node.get(0).asText();
    }

    public static String getMessageId(JsonNode node) {
        return node.get(1).asText();
    }

    public static String getAction(JsonNode node) {
        return node.get(2).asText();
    }

    public static JsonNode getPayload(JsonNode node) {
        return node.get(3);
    }
}
