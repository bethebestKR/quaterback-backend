package com.example.quaterback.websocket.transaction.event.entity;

import com.example.quaterback.websocket.transaction.event.domain.TransactionEventDomain;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_event")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionId;

    private String eventType;

    private String triggerReason;

    private Integer seqNo;

    private Integer evseId;

    private String vendorId;

    private String stationId;

    private LocalDateTime createdEventTimestamp;
    private LocalDateTime createdMeterTimestamp;
    private LocalDateTime updateEventTimestamp;
    private LocalDateTime updateMeterTimeStamp;

    private Integer sampledValue;

    public static TransactionEventEntity from(TransactionEventDomain domain) {
        return TransactionEventEntity.builder()
                .transactionId(domain.getTransactionInfo().getTransactionId())
                .eventType(domain.getEventType())
                .triggerReason(domain.getTriggerReason())
                .seqNo(domain.getSeqNo())
                .evseId(domain.extractEvseId())
                .vendorId(domain.extractVendorId())
                .stationId(domain.extractStationId())
                .createdEventTimestamp(domain.getCreatedEventTimestamp())
                .createdMeterTimestamp(domain.extractCreatedMeterValue())
                .updateEventTimestamp(domain.getUpdateEventTimeStamp())
                .updateMeterTimeStamp(domain.extractUpdatedMeterValue())
                .sampledValue(domain.extractMeterValue())
                .build();
    }
}