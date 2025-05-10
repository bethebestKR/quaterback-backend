package com.example.quaterback.api.domain.txlog.entity;

import com.example.quaterback.api.domain.charger.entity.ChargerEntity;
import com.example.quaterback.api.domain.txlog.domain.TransactionLogDomain;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tx_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionId;
    private LocalDateTime timestamp;
    private Integer meterValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evse_id")
    private ChargerEntity evseId;

    public static TransactionLogEntity fromTxLogDomain(TransactionLogDomain domain) {
        return TransactionLogEntity.builder()
                .transactionId(domain.getTransactionId())
                .timestamp(domain.getTimestamp())
                .meterValue(domain.getMeterValue())
                .build();
    }
}
