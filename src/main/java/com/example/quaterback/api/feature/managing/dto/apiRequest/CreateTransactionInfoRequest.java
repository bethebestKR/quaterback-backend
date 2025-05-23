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
    private String idToken;
    private String vehicleNo;
    private LocalDateTime startedTime;
    private LocalDateTime endedTime;
    private String stationId;
    private Integer evseId;
    private Double totalMeterValue;
    private Double totalPrice;

    public TransactionInfoDomain toDomain(){
        return TransactionInfoDomain.builder()
                .transactionId(transactionId)
                .idToken(idToken)
                .vehicleNo(vehicleNo)
                .startedTime(startedTime)
                .endedTime(endedTime)
                .stationId(stationId)
                .evseId(evseId)
                .totalMeterValue(totalMeterValue)
                .totalPrice(totalPrice)
                .build();
    }
}
