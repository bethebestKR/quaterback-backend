package com.example.quaterback.websocket;

import com.example.quaterback.api.domain.txinfo.service.TransactionInfoService;
import com.example.quaterback.common.annotation.Handler;
import com.example.quaterback.common.redis.service.RedisMapSessionToStationService;
import com.example.quaterback.websocket.mongodb.MongoDBService;
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
    private final TransactionInfoService transactionInfoService;
    private final MongoDBService mongoDBService;
    private final ReactWebSocketHandler reactWebSocketHandler;

    public OcppWebSocketHandler(List<OcppMessageHandler> handlers,
                                RedisMapSessionToStationService redisMappingService,
                                TransactionInfoService transactionInfoService,
                                MongoDBService mongoDBService,
                                ReactWebSocketHandler reactWebSocketHandler) {
        this.handlerMap = new HashMap<>();
        for (OcppMessageHandler handler : handlers) {
            handlerMap.put(handler.getAction(), handler);
        }
        this.redisMappingService = redisMappingService;
        this.transactionInfoService = transactionInfoService;
        this.mongoDBService = mongoDBService;
        this.reactWebSocketHandler = reactWebSocketHandler;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("WebSocket message received");

        for (WebSocketSession client : reactWebSocketHandler.getSessions()) {
            if (client.isOpen()) {
                client.sendMessage(message);
            }
        }
        log.info("WebSocket message sent to react");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(message.getPayload());

        String action = MessageUtil.getAction(jsonNode);

        if (!action.equals("BootNotification"))
            mongoDBService.saveMessage(objectMapper.writeValueAsString(jsonNode), redisMappingService.getStationId(session.getId()), action);

        OcppMessageHandler handler = handlerMap.get(action);
        if (handler != null) {
            JsonNode response = handler.handle(session, jsonNode);  // payload만 넘겨도 됨
            String stationId = redisMappingService.getStationId(session.getId());
            if (action.equals("BootNotification")) {
                mongoDBService.saveMessage(objectMapper.writeValueAsString(jsonNode), stationId, action);
            }
            session.sendMessage(new TextMessage(response.toString()));
            log.info("Sent StatusNotificationResponse: {}", response);

            for (WebSocketSession client : reactWebSocketHandler.getSessions()) {
                if (client.isOpen()) {
                    client.sendMessage(message);
                }
            }
            log.info("WebSocket message sent to react");

            mongoDBService.saveMessage(objectMapper.writeValueAsString(response), stationId, action);
        } else {
            log.warn("No handler found for action: {}", jsonNode);
            // optionally respond with error
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String stationId = redisMappingService.getStationId(session.getId());
        transactionInfoService.setErrorCodeToNotEndedTxInfos(stationId);
        redisMappingService.removeMapping(session.getId());
    }

    
}