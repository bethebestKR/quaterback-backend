package com.example.quaterback.websocket.boot.notification.handler;

import com.example.quaterback.api.domain.txinfo.entity.TransactionInfoEntity;
import com.example.quaterback.api.domain.txinfo.repository.SpringDataJpaTxInfoRepository;
import com.example.quaterback.common.annotation.Handler;
import com.example.quaterback.websocket.MessageUtil;
import com.example.quaterback.websocket.OcppMessageHandler;
import com.example.quaterback.websocket.RefreshTimeoutService;
import com.example.quaterback.websocket.boot.notification.service.BootNotificationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.Optional;

@Handler
@RequiredArgsConstructor
@Slf4j
public class BootNotificationHandler implements OcppMessageHandler {

    private final BootNotificationService bootNotificationService;
    private final ObjectMapper objectMapper;
    private final RefreshTimeoutService refreshTimeoutService;
    private final SpringDataJpaTxInfoRepository springDataJpaTxInfoRepository;

    @Override
    public String getAction() {
        return "BootNotification";
    }

    @Override
    public JsonNode handle(WebSocketSession session, JsonNode jsonNode) {
        String messageId = MessageUtil.getMessageId(jsonNode);
        JsonNode payload = MessageUtil.getPayload(jsonNode);
        String reason = payload.path("reason").asText();
        log.info("BootNotification reason - {}", reason);
        String sessionId = session.getId();

        String stationId = bootNotificationService.updateStationStatus(jsonNode, session.getId());
        refreshTimeoutService.refreshTimeout(sessionId);
        // 응답 메시지 생성
        ObjectMapper mapper = this.objectMapper;
        ArrayNode response = mapper.createArrayNode();
        response.add(3);  // MessageTypeId for CALL_RESULT
        response.add(messageId);  // 요청에서 가져온 messageId

        ObjectNode payloadNode = mapper.createObjectNode();
        payloadNode.put("currentTime", LocalDateTime.now().toString());  // 또는 UTC 포맷 사용
        payloadNode.put("interval", 15);
        payloadNode.put("status", "Accepted");

        ObjectNode customDataNode = mapper.createObjectNode();
        Optional<TransactionInfoEntity> entity = springDataJpaTxInfoRepository.findFirstByOrderByTransactionIdDesc();
        if (entity.isEmpty()) {
            customDataNode.put("transactionId", "tx-000");
        } else {
            customDataNode.put("transactionId", entity.get().getTransactionId());
        }
        // payload에 customData 추가
        payloadNode.put("customData", customDataNode);
        response.add(payloadNode);

        return response;
    }
}
