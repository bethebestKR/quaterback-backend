package com.example.quaterback.websocket.transaction.event.converter;

import com.example.quaterback.common.annotation.Converter;
import com.example.quaterback.websocket.MessageUtil;
import com.example.quaterback.websocket.transaction.event.domain.TransactionEventDomain;
import com.example.quaterback.websocket.transaction.event.domain.sub.*;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDateTime;
import java.util.List;

@Converter
public class TransactionEventConverter {
    public TransactionEventDomain convertToStartedDomain(JsonNode jsonNode) {
        String messageId = MessageUtil.getMessageId(jsonNode);
        String messageTypeId = MessageUtil.getMessageTypeId(jsonNode);
        String action = MessageUtil.getAction(jsonNode);
        JsonNode payload = MessageUtil.getPayload(jsonNode);

        return TransactionEventDomain.builder()
                .messageId(messageId)
                .messageTypeId(messageTypeId)
                .action(action)
                .eventType(payload.path("eventType").asText())
                .triggerReason(payload.path("triggerReason").asText())
                .seqNo(payload.path("seqNo").asInt())
                .createdEventTimestamp(LocalDateTime.parse(payload.path("timestamp").asText()))
                .updateEventTimeStamp(LocalDateTime.now())
                .transactionInfo(new TransactionInfo(payload.path("transactionInfo").path("transactionId").asText()))
                .evse(new Evse(payload.path("evse").path("id").asInt()))
                .customData(new CustomData(payload.path("customData").path("vendorId").asText(), payload.path("customData").path("stationId").asText()))
                .meterValue(List.of(
                        MeterValue.builder()
                                .createdMeterTimestamp(LocalDateTime.parse(payload.path("meterValue").get(0).path("timestamp").asText()))
                                .sampledValues(List.of(new SampledValue(payload.path("meterValue").get(0).path("sampledValue").get(0).path("value").asInt())))
                                .build()
                ))
                .build();
    }
}
