package com.example.quaterback.websocket.transaction.event.service;

import com.example.quaterback.redis.service.RedisMapSessionToStationService;
import com.example.quaterback.txinfo.domain.TransactionInfoDomain;
import com.example.quaterback.txinfo.repository.TxInfoRepository;
import com.example.quaterback.txlog.domain.TransactionLogDomain;
import com.example.quaterback.txlog.repository.TxLogRepository;
import com.example.quaterback.websocket.transaction.event.converter.TransactionEventConverter;
import com.example.quaterback.websocket.transaction.event.domain.TransactionEventDomain;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionEventService {

    private final TransactionEventConverter converter;
    private final TxInfoRepository txInfoRepository;
    private final TxLogRepository txLogRepository;
    private final RedisMapSessionToStationService redisMappingService;
    public String saveTxInfo(JsonNode jsonNode, String sessionId) {
        TransactionEventDomain txEventDomain = converter.convertToStartedTransactionDomain(jsonNode);

        String stationId = redisMappingService.getStationId(sessionId);
        TransactionInfoDomain txInfoDomain = TransactionInfoDomain.fromStartedTxEventDomain(txEventDomain, stationId);

        String resultTransactionId = txInfoRepository.save(txInfoDomain);
        return resultTransactionId;
    }

    public String saveTxLog(JsonNode jsonNode) {
        TransactionEventDomain txEventDomain = converter.convertToNonStartedTransactionDomain(jsonNode);

        TransactionLogDomain txLogDomain = TransactionLogDomain.fromTxEventDomain(txEventDomain);

        String resultTransactionId = txLogRepository.save(txLogDomain);
        return resultTransactionId;
    }

    @Transactional
    public String updateTxEndTime(JsonNode jsonNode) {
        saveTxLog(jsonNode);

        TransactionEventDomain txEventDomain = converter.convertToNonStartedTransactionDomain(jsonNode);

        Integer totalMeterValue = txLogRepository.getTotalMeterValue(txEventDomain.extractTransactionId());
        Integer totalPrice =  (int) (totalMeterValue * 0.5);

        TransactionInfoDomain txInfoDomain = TransactionInfoDomain.fromEndedTxEventDomain(txEventDomain, totalMeterValue, totalPrice);
        String resultTransactionId = txInfoRepository.updateEndTime(txInfoDomain);

        return resultTransactionId;
    }

}
