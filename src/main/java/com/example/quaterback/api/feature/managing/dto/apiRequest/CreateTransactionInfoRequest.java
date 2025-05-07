package com.example.quaterback.api.feature.managing.dto.apiRequest;

import com.example.quaterback.api.domain.txinfo.domain.TransactionInfoDomain;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class CreateTransactionInfoRequest {
    private String transactionId;
    private String userId;
    private String vehicleNo;
    private LocalDateTime startedTime;
    private LocalDateTime endedTime;
    private String stationId;
    private Integer evseId;
    private Integer totalMeterValue;
    private Integer totalPrice;

    public TransactionInfoDomain toDomain(){
        return TransactionInfoDomain.builder()
                .transactionId(transactionId)
                .startedTime(startedTime)
                .vehicleNo(vehicleNo)
                .userId(userId)
                .stationId(stationId)
                .evseId(evseId)
                .totalMeterValue(totalMeterValue)
                .totalPrice(totalPrice)
                .build();
    }
}
