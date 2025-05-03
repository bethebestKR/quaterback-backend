package com.example.quaterback.websocket.meter.value.handler;

import com.example.quaterback.common.annotation.Handler;
import com.example.quaterback.websocket.MessageUtil;
import com.example.quaterback.websocket.OcppMessageHandler;
import com.example.quaterback.websocket.meter.value.service.MeterValuesService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Handler
@RequiredArgsConstructor
@Slf4j
public class MeterValuesHandler implements OcppMessageHandler {

    private final MeterValuesService meterValueService;
    @Override
    public String getAction() {
        return "MeterValues";
    }

    @Override
    public void handle(WebSocketSession session, JsonNode jsonNode) throws IOException {
        String messageId = MessageUtil.getMessageId(jsonNode);
        JsonNode payload = MessageUtil.getPayload(jsonNode);

        log.info("MeterValues value : ");

        String sessionId = session.getId();

        String stationId = meterValueService.updateStationEss(jsonNode, sessionId);

    }
}
