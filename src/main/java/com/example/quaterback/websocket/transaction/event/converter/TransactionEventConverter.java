package com.example.quaterback.websocket.transaction.event.converter;

import com.example.quaterback.api.feature.dashboard.dto.query.ChargerUsageQuery;
import com.example.quaterback.api.feature.dashboard.dto.query.DashboardSummaryQuery;
import com.example.quaterback.api.feature.dashboard.dto.response.ChargerUsageResponse;
import com.example.quaterback.api.feature.dashboard.dto.response.DashboardSummaryResponse;
import com.example.quaterback.api.feature.dashboard.dto.response.HourlyDischargeResponse;
import com.example.quaterback.common.annotation.Converter;
import com.example.quaterback.websocket.MessageUtil;
import com.example.quaterback.websocket.sub.MeterValue;
import com.example.quaterback.websocket.transaction.event.domain.TransactionEventDomain;
import com.example.quaterback.websocket.transaction.event.domain.sub.*;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Converter
public class TransactionEventConverter {
    public TransactionEventDomain convertToStartedTransactionDomain(JsonNode jsonNode) {
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
                .timestamp(LocalDateTime.parse(payload.path("timestamp").asText()))
                .transactionInfo(new TransactionInfo(payload.path("transactionInfo").path("transactionId").asText()))
                .evse(new Evse(payload.path("evse").path("id").asInt()))
                .txIdToken(new TxIdToken(
                        payload.path("idToken").path("idToken").asText(),
                        payload.path("idToken").path("type").asText()))
                .customData(new TransactionCustomData(
                        payload.path("customData").path("vendorId").asText(),
                        new VehicleInfo(
                                payload.path("customData").path("vehicleInfo").path("vehicleNo").asText(),
                                payload.path("customData").path("vehicleInfo").path("model").asText(),
                                payload.path("customData").path("vehicleInfo").path("batteryCapacityKWh").asInt(),
                                payload.path("customData").path("vehicleInfo").path("requestedEnergyKWh").asInt())
                        )
                )
                .build();
    }

    public TransactionEventDomain convertToNonStartedTransactionDomain(JsonNode jsonNode) {
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
                .timestamp(LocalDateTime.parse(payload.path("timestamp").asText()))
                .transactionInfo(new TransactionInfo(payload.path("transactionInfo").path("transactionId").asText()))
                .evse(new Evse(payload.path("evse").path("id").asInt()))
                .txIdToken(new TxIdToken(
                        payload.path("idToken").path("idToken").asText(),
                        payload.path("idToken").path("type").asText()))
                .meterValue(List.of(
                        MeterValue.forTransaction(payload.path("meterValue").get(0))
                ))
                .build();
    }

    public List<HourlyDischargeResponse> toHourlyDischargeResponseList(List<Object[]> raw) {
        Map<Integer, Integer> hourMap = new HashMap<>();
        for (Object[] row : raw) {
            int hour = ((Number) row[0]).intValue();
            int value = ((Number) row[1]).intValue();
            hourMap.put(hour, value);
        }

        return IntStream.range(0, 24)
                .mapToObj(h -> new HourlyDischargeResponse(h, hourMap.getOrDefault(h, 0)))
                .toList();
    }

    public DashboardSummaryResponse toDashboardSummaryResponse(DashboardSummaryQuery query){
        return DashboardSummaryResponse.from(query);
    }

    public List<ChargerUsageResponse> toChargerUsageResponseList(List<ChargerUsageQuery> queryList) {
        return queryList.stream()
                .map(ChargerUsageResponse::from)
                .toList();
    }
}
