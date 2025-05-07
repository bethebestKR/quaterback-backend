package com.example.quaterback.websocket.heart.beat;

import com.example.quaterback.common.annotation.Handler;
import com.example.quaterback.websocket.MessageUtil;
import com.example.quaterback.websocket.OcppMessageHandler;
import com.example.quaterback.websocket.RefreshTimeoutService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Handler
@RequiredArgsConstructor
public class HeartBeatHandler implements OcppMessageHandler {

    private final RefreshTimeoutService refreshTimeoutService;
    @Override
    public String getAction() {
        return "HeartBeat";
    }

    @Override
    public void handle(WebSocketSession session, JsonNode message) throws IOException {
        String MessageTypeId = MessageUtil.getMessageTypeId(message);
        String MessageId = MessageUtil.getMessageId(message);
        String action = MessageUtil.getAction(message);
        String sessionId = session.getId();

        refreshTimeoutService.refreshTimeout(sessionId);

    }

    //Response 보내기

}
