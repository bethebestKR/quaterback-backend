package com.example.quaterback.websocket.transaction.event.converter;

import com.example.quaterback.annotation.Converter;
import com.example.quaterback.websocket.transaction.event.domain.TransactionEventDomain;
import com.example.quaterback.websocket.transaction.event.domain.sub.*;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Converter
public class TransactionEventConverter {
    public TransactionEventDomain convertToStartedDomain(JsonNode jsonNode) {
        JsonNode payload = jsonNode.path("payload");

        return TransactionEventDomain.builder()
                .messageId(jsonNode.path("messageId").asText())
                .messageTypeId(jsonNode.path("messageTypeId").asText())
                .action(jsonNode.path("action").asText())
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
