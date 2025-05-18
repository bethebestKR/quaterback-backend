package com.example.quaterback.websocket.authorize.handler;

import com.example.quaterback.common.annotation.Handler;
import com.example.quaterback.websocket.MessageUtil;
import com.example.quaterback.websocket.OcppMessageHandler;
import com.example.quaterback.websocket.authorize.service.AuthorizeService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Handler
public class AuthorizeHandler implements OcppMessageHandler {

    private final AuthorizeService authorizeService;
    private final ObjectMapper objectMapper;

    @Override
    public String getAction() {
        return "Authorize";
    }

    @Override
    public JsonNode handle(WebSocketSession session, JsonNode jsonNode) throws IOException {
        String messageId = MessageUtil.getMessageId(jsonNode);
        JsonNode payload = MessageUtil.getPayload(jsonNode);
        String type = payload.path("idToken").path("type").asText();
        log.info("Authorize idToken - {}", type);

        boolean isAccepted = authorizeService.authorize(jsonNode);

        //response
        ArrayNode response = objectMapper.createArrayNode();
        response.add(3); // MessageTypeId
        response.add(messageId); // MessageId

        ObjectNode idTokenInfo = objectMapper.createObjectNode();
        if (isAccepted) {
            idTokenInfo.put("status", "Accepted");
        }
        else idTokenInfo.put("status", "Invalid");
        log.info("Authorize result: {}", idTokenInfo);

        ObjectNode responseBody = objectMapper.createObjectNode();
        responseBody.set("idTokenInfo", idTokenInfo);

        response.add(responseBody);

        return response;
    }
}
