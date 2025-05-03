package com.example.quaterback.websocket.transaction.event.handler;

import com.example.quaterback.common.annotation.Handler;
import com.example.quaterback.websocket.MessageUtil;
import com.example.quaterback.websocket.OcppMessageHandler;
import com.example.quaterback.websocket.transaction.event.service.TransactionEventService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.Instant;

@Handler
@RequiredArgsConstructor
@Slf4j
public class TransactionEventHandler implements OcppMessageHandler {

    private final TransactionEventService transactionEventService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String getAction() {
        return "TransactionEvent";
    }

    @Override
    public void handle(WebSocketSession session, JsonNode jsonNode) throws IOException {
        String messageId = MessageUtil.getMessageId(jsonNode);
        JsonNode payload = MessageUtil.getPayload(jsonNode);
        String eventType = payload.path("eventType").asText();
        log.info(eventType);

        switch (eventType) {
            case "Started":
                break;
            case "Updated":
                // 처리 예정
                Long id = transactionEventService.create(jsonNode);
                sendTransactionEventStarted(session, messageId);
                break;
            case "Ended":
                // 처리 예정
                break;
            default:
                // 무시
                break;
        }
    }

    //이거는 반환 메세지 정의 추후 리펙터링 예정
    private void sendTransactionEventStarted(WebSocketSession session, String messageId) throws IOException {
        ObjectNode response = objectMapper.createObjectNode();
        response.put("messageId", messageId);
        response.put("messageType", "CallResult");

        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("currentTime", Instant.now().toString());
        payload.put("status", "Accepted");
        payload.put("interval", 300);

        response.set("payload", payload);

        String jsonResponse = objectMapper.writeValueAsString(response);

        log.info("Sending TransactionEvent Started Response to {}: {}",
                session.getUri(), jsonResponse);

        session.sendMessage(new TextMessage(jsonResponse));
    }
}