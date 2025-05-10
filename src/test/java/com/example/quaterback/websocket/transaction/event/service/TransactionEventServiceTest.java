package com.example.quaterback.websocket.transaction.event.service;

import com.example.quaterback.api.domain.txinfo.domain.TransactionInfoDomain;
import com.example.quaterback.api.domain.txinfo.repository.TxInfoRepository;
import com.example.quaterback.api.domain.txlog.domain.TransactionLogDomain;
import com.example.quaterback.api.domain.txlog.repository.TxLogRepository;
import com.example.quaterback.common.redis.service.RedisMapSessionToStationService;
import com.example.quaterback.websocket.transaction.event.converter.TransactionEventConverter;
import com.example.quaterback.websocket.transaction.event.domain.TransactionEventDomain;
import com.example.quaterback.websocket.transaction.event.fixture.TransactionEventFixture;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class TransactionEventServiceTest {

    @InjectMocks
    private TransactionEventService transactionEventService;

    @Mock
    private RedisMapSessionToStationService redisMappingService;

    @Mock
    private TransactionEventConverter converter;

    @Mock
    private TxInfoRepository txInfoRepository;

    @Mock
    private TxLogRepository txLogRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveTxInfo_Started_TransactionEventDomain에서_필요한_내용을_저장한다() {
        //given
        Integer messageType = 2;
        String messageId = "123";
        String action = "TransactionEvent";
        String eventType = "Started";
        String triggerReason = "CablePluggedIn";
        LocalDateTime timestamp = LocalDateTime.now();
        Integer seqNo = 1;
        Integer id = 1;
        String transactionId = "tx-001";
        String idToken = "token123";
        String vehicleNo = "234-234";
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
        TransactionEventDomain domain = TransactionEventFixture.createExpectedStartedDomain(
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
        String stationId = "station1";
        String sessionId = "session1";

        given(converter.convertToStartedTransactionDomain(jsonNode)).willReturn(domain);
        given(redisMappingService.getStationId(sessionId)).willReturn(stationId);
        TransactionInfoDomain infoDomain = TransactionInfoDomain.fromStartedTxEventDomain(domain, stationId);
        given(txInfoRepository.save(any(TransactionInfoDomain.class))).willReturn(infoDomain.getTransactionId());

        // when
        String result = transactionEventService.saveTxInfo(jsonNode, sessionId);

        // then
        assertThat(result).isEqualTo(infoDomain.getTransactionId());
    }

    @Test
    void saveTxLog_Updated_TransactionEventDomain에서_필요한_내용을_저장한다() {
        //given
        Integer messageType = 2;
        String messageId = "123";
        String action = "TransactionEvent";
        String eventType = "Updated";
        String triggerReason = "CablePluggedIn";
        LocalDateTime timestamp = LocalDateTime.now();
        Integer seqNo = 2;
        Integer id = 1;
        String transactionId = "tx-001";
        String idToken = "token123";
        Integer value = 20000;
        JsonNode jsonNode = TransactionEventFixture.createNonStartedTransactionEventJsonNode(
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
                value
        );
        TransactionEventDomain domain = TransactionEventFixture.createExpectedNonStartedDomain(
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
                value
        );

        given(converter.convertToNonStartedTransactionDomain(jsonNode)).willReturn(domain);
        TransactionLogDomain logDomain = TransactionLogDomain.fromTxEventDomain(domain);
        given(txLogRepository.save(any(TransactionLogDomain.class))).willReturn(logDomain.getTransactionId());

        // when
        String result = transactionEventService.saveTxLog(jsonNode);

        // then
        assertThat(result).isEqualTo(logDomain.getTransactionId());
    }

    @Test
    void updateTxEndTime_Ended_TransactionEventDomain에서_필요한_값들_저장_및_수정() {
        // given
        Integer messageType = 2;
        String messageId = "123";
        String action = "TransactionEvent";
        String eventType = "Ended";
        String triggerReason = "CablePluggedIn";
        LocalDateTime timestamp = LocalDateTime.now();
        Integer seqNo = 2;
        Integer id = 1;
        String transactionId = "tx-001";
        String idToken = "token123";
        Integer value = 20000;
        JsonNode jsonNode = TransactionEventFixture.createNonStartedTransactionEventJsonNode(
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
                value
        );
        TransactionEventDomain domain = TransactionEventFixture.createExpectedNonStartedDomain(
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
                value
        );

        given(converter.convertToNonStartedTransactionDomain(jsonNode)).willReturn(domain);
        TransactionLogDomain logDomain = TransactionLogDomain.fromTxEventDomain(domain);
        given(txLogRepository.save(any())).willReturn(logDomain.getTransactionId());

        given(txLogRepository.getTotalMeterValue(any())).willReturn(1000);
        TransactionInfoDomain infoDomain = TransactionInfoDomain.fromEndedTxEventDomain(domain, 1000, 1000);
        given(txInfoRepository.updateEndTime(any(TransactionInfoDomain.class))).willReturn(infoDomain.getTransactionId());

        // when
        String result = transactionEventService.updateTxEndTime(jsonNode);

        // then
        assertThat(result).isEqualTo(infoDomain.getTransactionId());
    }

}