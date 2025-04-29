package com.example.quaterback.websocket.transaction.event.domain;

import com.example.quaterback.websocket.transaction.event.domain.sub.CustomData;
import com.example.quaterback.websocket.transaction.event.domain.sub.Evse;
import com.example.quaterback.websocket.transaction.event.domain.sub.MeterValue;
import com.example.quaterback.websocket.transaction.event.domain.sub.TransactionInfo;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionEventDomain {
    private String messageTypeId;
    private String messageId;
    private String action;

    private String eventType;
    private LocalDateTime createdEventTimestamp;
    private LocalDateTime updateEventTimeStamp;
    private String triggerReason;
    private Integer seqNo;

    private TransactionInfo transactionInfo;
    private Evse evse;
    private CustomData customData;
    private List<MeterValue> meterValue;

    public String extractTransactionId() {
        return transactionInfo.getTransactionId();
    }

    public Integer extractEvseId() {
        return evse.getId();
    }

    public String extractVendorId() {
        return customData.getVendorId();
    }

    public String extractStationId() {
        return customData.getStationId();
    }

    public LocalDateTime extractCreatedMeterValue() {
        return meterValue.get(0).getCreatedMeterTimestamp();
    }

    public LocalDateTime extractUpdatedMeterValue() {
        return meterValue.get(0).getUpdatedMeterTimestamp();
    }

    public Integer extractMeterValue() {
        return meterValue.get(0).getSampledValues().get(0).getValue();
    }

}
