package com.example.quaterback.api.domain.txinfo.domain;

import com.example.quaterback.api.domain.txinfo.entity.TransactionInfoEntity;
import com.example.quaterback.websocket.transaction.event.domain.TransactionEventDomain;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionInfoDomain {
    private String transactionId;
    private LocalDateTime startedTime;
    private LocalDateTime endedTime;
    private String vehicleNo;
    private String idToken;
    private String stationId;
    private Integer evseId;
    private Double totalMeterValue;
    private Double totalPrice;
    private String errorCode;

    public static TransactionInfoDomain fromStartedTxEventDomain(TransactionEventDomain domain, String stationId) {
        return TransactionInfoDomain.builder()
                .transactionId(domain.extractTransactionId())
                .startedTime(domain.getTimestamp())
                .vehicleNo(domain.extractVehicleNo())
                .idToken(domain.extractUserId())
                .stationId(stationId)
                .evseId(domain.extractEvseId())
                .build();
    }

    public static TransactionInfoDomain fromEndedTxEventDomain(String transactionId, LocalDateTime endedTime, String errorCode, Double totalMeterValue, Double totalPrice) {
        return TransactionInfoDomain.builder()
                .transactionId(transactionId)
                .endedTime(endedTime)
                .totalMeterValue(totalMeterValue)
                .totalPrice(totalPrice)
                .errorCode(errorCode)
                .build();
    }

    public static TransactionInfoDomain transactionIdDomain(String transactionId){
        return TransactionInfoDomain.builder()
                .transactionId(transactionId)
                .build();
    }
    public static TransactionInfoDomain fromTxEntityDomain(TransactionInfoEntity entity){
        return TransactionInfoDomain.builder()
                .transactionId(entity.getTransactionId())
                .endedTime(entity.getEndedTime())
                .startedTime(entity.getStartedTime())
                .stationId(entity.getStationId())
                .vehicleNo(entity.getVehicleNo())
                .idToken(entity.getIdToken())
                .evseId(entity.getEvseId().getEvseId())
                .totalMeterValue(entity.getTotalMeterValue())
                .totalPrice(entity.getTotalPrice())
                .errorCode(entity.getErrorCode())
                .build();
    }

    public static TransactionInfoDomain fromLocalDateTimeToDomain(
            LocalDateTime start
            ,LocalDateTime end)
    {
        return TransactionInfoDomain.builder()
                .startedTime(start)
                .endedTime(end)
                .build();
    }
}
