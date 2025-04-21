package com.example.quaterback.websocket;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public interface OcppMessageHandler {
    String getAction();

    void handle(WebSocketSession session, JsonNode message) throws IOException;
}
