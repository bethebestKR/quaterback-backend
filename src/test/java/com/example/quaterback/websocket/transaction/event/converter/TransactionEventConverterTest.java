package com.example.quaterback.websocket.transaction.event.converter;

import com.example.quaterback.websocket.transaction.event.domain.TransactionEventDomain;
import com.example.quaterback.websocket.transaction.event.fixture.TransactionEventFixture;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class TransactionEventConverterTest {

    private TransactionEventConverter converter;

    @BeforeEach
    void setUp() {
        converter = new TransactionEventConverter();
    }

    @Test
    void convertToStartedTransactionDomain_전달_받은_jsonNode를_Domain으로_변환() {

        //given
        Integer messageType = 2;
        String messageId = "tx-msg-001";
        String action = "TransactionEvent";
        String eventType = "Started";
        String triggerReason = "CablePluggedIn";
        LocalDateTime timestamp = LocalDateTime.of(2025,4,20,16,30,00);
        Integer seqNo = 1;
        Integer id = 1;
        String transactionId = "tx-001";
        String idToken = "1234";
        String vehicleNo = "38-632";
        String model = "GV-60";
        Integer batteryCapacityKWh = 72000;
        Integer requestedEnergyKWh = 30000;
        JsonNode jsonNode = TransactionEventFixture.createStartedTransactionEventJsonNode(
                messageType,
                messageId,
                action,
                eventType,
                triggerReason,
                timestamp,
                seqNo,
                id,
                transactionId,
                idToken,
                vehicleNo,
                model,
                batteryCapacityKWh,
                requestedEnergyKWh
        );
        TransactionEventDomain expected = TransactionEventFixture.createExpectedStartedDomain(
                messageType.toString(),
                messageId,
                action,
                eventType,
                timestamp,
                triggerReason,
                seqNo,
                transactionId,
                id,
                idToken,
                vehicleNo,
                model,
                batteryCapacityKWh,
                requestedEnergyKWh
        );

        //when
        TransactionEventDomain result = converter.convertToStartedTransactionDomain(jsonNode);

        //then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expected);

    }

}