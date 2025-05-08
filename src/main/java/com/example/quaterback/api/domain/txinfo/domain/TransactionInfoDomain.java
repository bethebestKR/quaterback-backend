package com.example.quaterback.api.domain.txinfo.domain;

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
    private Integer totalMeterValue;
    private Integer totalPrice;

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

    public static TransactionInfoDomain fromEndedTxEventDomain(TransactionEventDomain domain, Integer totalMeterValue, Integer totalPrice) {
        return TransactionInfoDomain.builder()
                .transactionId(domain.extractTransactionId())
                .endedTime(domain.getTimestamp())
                .totalMeterValue(totalMeterValue)
                .totalPrice(totalPrice)
                .build();
    }

}
