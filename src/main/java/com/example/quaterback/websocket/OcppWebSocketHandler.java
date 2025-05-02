package com.example.quaterback.websocket;

import com.example.quaterback.common.annotation.Handler;
import com.example.quaterback.redis.service.RedisMapSessionToStationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Handler
@Slf4j
public class OcppWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, OcppMessageHandler> handlerMap;
    private final RedisMapSessionToStationService redisMappingService;

    public OcppWebSocketHandler(List<OcppMessageHandler> handlers, RedisMapSessionToStationService redisMappingService) {
        this.handlerMap = new HashMap<>();
        for (OcppMessageHandler handler : handlers) {
            handlerMap.put(handler.getAction(), handler);
        }
        this.redisMappingService = redisMappingService;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("WebSocket message received");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(message.getPayload());

        String action = MessageUtil.getAction(jsonNode);

        OcppMessageHandler handler = handlerMap.get(action);
        if (handler != null) {
            handler.handle(session, jsonNode);  // payload만 넘겨도 됨
        } else {
            log.warn("No handler found for action: {}", jsonNode);
            // optionally respond with error
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        redisMappingService.removeMapping(session.getId());
    }
}