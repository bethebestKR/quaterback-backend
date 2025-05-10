package com.example.quaterback.websocket.authorize.converter;

import com.example.quaterback.common.annotation.Converter;
import com.example.quaterback.websocket.MessageUtil;
import com.example.quaterback.websocket.authorize.domain.AuthorizeDomain;
import com.example.quaterback.websocket.sub.SubIdToken;
import com.fasterxml.jackson.databind.JsonNode;

@Converter
public class AuthorizeConverter {
    public AuthorizeDomain convertToAuthorizeDomain(JsonNode jsonNode) {
        String messageId = MessageUtil.getMessageId(jsonNode);
        String messageTypeId = MessageUtil.getMessageTypeId(jsonNode);
        String action = MessageUtil.getAction(jsonNode);
        JsonNode payload = MessageUtil.getPayload(jsonNode);

        return AuthorizeDomain.builder()
                .messageTypeId(messageTypeId)
                .messageId(messageId)
                .action(action)
                .authIdToken(new SubIdToken(
                        payload.path("idToken").path("idToken").asText(),
                        payload.path("idToken").path("type").asText()))
                .build();
    }
}
