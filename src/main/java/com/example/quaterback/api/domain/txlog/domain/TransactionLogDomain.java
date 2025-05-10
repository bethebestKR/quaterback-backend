package com.example.quaterback.api.domain.txlog.domain;

import com.example.quaterback.websocket.transaction.event.domain.TransactionEventDomain;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionLogDomain {
    private String transactionId;
    private LocalDateTime timestamp;
    private Integer meterValue;

    public static TransactionLogDomain fromTxEventDomain(TransactionEventDomain domain) {
        return TransactionLogDomain.builder()
                .transactionId(domain.extractTransactionId())
                .timestamp(domain.getTimestamp())
                .meterValue(domain.extractMeterValue())
                .build();
    }
}
