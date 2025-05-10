package com.example.quaterback.websocket.transaction.event.fixture;

import com.example.quaterback.websocket.sub.MeterValue;
import com.example.quaterback.websocket.sub.SampledValue;
import com.example.quaterback.websocket.sub.SubIdToken;
import com.example.quaterback.websocket.transaction.event.domain.TransactionEventDomain;
import com.example.quaterback.websocket.transaction.event.domain.sub.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.time.LocalDateTime;
import java.util.List;

public class TransactionEventFixture {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static JsonNode createStartedTransactionEventJsonNode(
            Integer messageType,
            String messageId,
            String action,
            String eventType,
            String triggerReason,
            LocalDateTime timestamp,
            Integer seqNo,
            Integer id,
            String transactionId,
            String idToken,
            String vehicleNo,
            String model,
            Integer batteryCapacityKWh,
            Integer requestedEnergyKWh
    ) {
        ArrayNode rootArray = mapper.createArrayNode();

        rootArray.add(messageType);
        rootArray.add(messageId);
        rootArray.add(action);

        // Payload Object
        ObjectNode payload = mapper.createObjectNode();
        payload.put("eventType", eventType);
        payload.put("timestamp", timestamp.toString());
        payload.put("triggerReason", triggerReason);
        payload.put("seqNo", seqNo);

        // transactionInfo
        ObjectNode transactionInfo = mapper.createObjectNode();
        transactionInfo.put("transactionId", transactionId);
        payload.set("transactionInfo", transactionInfo);

        // evse
        ObjectNode evse = mapper.createObjectNode();
        evse.put("id", id);
        payload.set("evse", evse);

        // idToken
        ObjectNode txIdToken = mapper.createObjectNode();
        txIdToken.put("idToken", idToken);
        txIdToken.put("type", "Central");
        payload.set("idToken", txIdToken);

        // customData
        ObjectNode customData = mapper.createObjectNode();
        customData.put("vendorId", "quarterback");

        // vehicleInfo
        ObjectNode vehicleInfo = mapper.createObjectNode();
        vehicleInfo.put("vehicleNo", vehicleNo);
        vehicleInfo.put("model", model);
        vehicleInfo.put("batteryCapacityKWh", batteryCapacityKWh);
        vehicleInfo.put("requestedEnergyKWh", requestedEnergyKWh);

        customData.set("vehicleInfo", vehicleInfo);
        payload.set("customData", customData);

        rootArray.add(payload);

        return rootArray;
    }

    public static TransactionEventDomain createExpectedStartedDomain(
            String messageTypeId,
            String messageId,
            String action,
            String eventType,
            LocalDateTime timestamp,
            String triggerReason,
            Integer seqNo,
            String transactionId,
            Integer id,
            String idToken,
            String vehicleNo,
            String model,
            Integer batteryCapacityKWh,
            Integer requestedEnergyKWh
    ) {
        return new TransactionEventDomain(
                messageTypeId,
                messageId,
                action,
                eventType,
                timestamp,
                triggerReason,
                seqNo,
                new TransactionInfo(transactionId),
                new Evse(id),
                new SubIdToken(idToken, "Central"),
                new TransactionCustomData("quarterback", new VehicleInfo(vehicleNo,model,batteryCapacityKWh,requestedEnergyKWh)),
                null
        );
    }

    public static JsonNode createNonStartedTransactionEventJsonNode(
            Integer messageType,
            String messageId,
            String action,
            String eventType,
            String triggerReason,
            LocalDateTime timestamp,
            Integer seqNo,
            Integer id,
            String transactionId,
            String idToken,
            Integer value
    ) {
        ArrayNode rootArray = mapper.createArrayNode();

        rootArray.add(messageType);
        rootArray.add(messageId);
        rootArray.add(action);

        ObjectNode payload = mapper.createObjectNode();
        payload.put("eventType", eventType);
        payload.put("timestamp", timestamp.toString());
        payload.put("triggerReason", triggerReason);
        payload.put("seqNo", seqNo);

        ObjectNode transactionInfo = mapper.createObjectNode();
        transactionInfo.put("transactionId", transactionId);
        payload.set("transactionInfo", transactionInfo);

        ObjectNode evse = mapper.createObjectNode();
        evse.put("id", id);
        payload.set("evse", evse);

        ObjectNode idTokenNode = mapper.createObjectNode();
        idTokenNode.put("idToken", idToken);
        idTokenNode.put("type", "Central");
        payload.set("idToken", idTokenNode);

        // meterValue 구성
        ObjectNode sampledValue = mapper.createObjectNode();
        sampledValue.put("value", value);

        ArrayNode sampledValuesArray = mapper.createArrayNode();
        sampledValuesArray.add(sampledValue);

        ObjectNode meterValueEntry = mapper.createObjectNode();
        meterValueEntry.put("timestamp", timestamp.toString());
        meterValueEntry.set("sampledValue", sampledValuesArray);

        ArrayNode meterValuesArray = mapper.createArrayNode();
        meterValuesArray.add(meterValueEntry);

        payload.set("meterValue", meterValuesArray);

        rootArray.add(payload);

        return rootArray;
    }

    public static TransactionEventDomain createExpectedNonStartedDomain(
            String messageTypeId,
            String messageId,
            String action,
            String eventType,
            LocalDateTime timestamp,
            String triggerReason,
            Integer seqNo,
            String transactionId,
            Integer id,
            String idToken,
            Integer value
    ) {
        return new TransactionEventDomain(
                messageTypeId,
                messageId,
                action,
                eventType,
                timestamp,
                triggerReason,
                seqNo,
                new TransactionInfo(transactionId),
                new Evse(id),
                new SubIdToken(idToken, "Central"),
                null,
                List.of(new MeterValue(
                        timestamp,
                        List.of(new SampledValue(value, null))
                ))
        );
    }

}
