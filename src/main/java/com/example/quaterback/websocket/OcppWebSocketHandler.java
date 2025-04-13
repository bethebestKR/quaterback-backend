package com.example.quaterback.websocket;

import com.example.quaterback.annotation.Handler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Handler
public class OcppWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, OcppMessageHandler> handlerMap;

    public OcppWebSocketHandler(List<OcppMessageHandler> handlers) {
        this.handlerMap = new HashMap<>();
        for (OcppMessageHandler handler : handlers) {
            handlerMap.put(handler.getAction(), handler);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        JsonNode jsonNode = new ObjectMapper().readTree(message.getPayload());

        String action = jsonNode.path("action").asText();

        OcppMessageHandler handler = handlerMap.get(action);
        if (handler != null) {
            handler.handle(session, jsonNode);
        } else {
            // 기본 처리 or 에러 응답
        }
    }
}