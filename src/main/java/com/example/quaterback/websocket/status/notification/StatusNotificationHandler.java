package com.example.quaterback.websocket.status.notification;

import com.example.quaterback.annotation.Handler;
import com.example.quaterback.websocket.OcppMessageHandler;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Handler
public class StatusNotificationHandler implements OcppMessageHandler {

    @Override
    public String getAction() {
        return "StatusNotification";
    }

    @Override
    public void handle(WebSocketSession session, JsonNode message) throws IOException {

    }
}
