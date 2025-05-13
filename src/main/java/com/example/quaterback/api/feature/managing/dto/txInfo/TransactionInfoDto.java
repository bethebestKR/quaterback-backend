package com.example.quaterback.api.feature.managing.dto.txInfo;

import com.example.quaterback.api.domain.txinfo.domain.TransactionInfoDomain;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionInfoDto {

    private final String transactionId;
    private final String idToken;
    private final String vehicleNo;
    private final ChargeSummary chargeSummary;

    @Data
    public static class ChargeSummary {
        private final LocalDateTime startedTime;
        private final LocalDateTime endedTime;
        private final Double totalMeterValue;
        private final Double totalPrice;

        public ChargeSummary(LocalDateTime startedTime, LocalDateTime endedTime, Double totalMeterValue, Double totalPrice) {
            this.startedTime = startedTime;
            this.endedTime = endedTime;
            this.totalMeterValue = totalMeterValue;
            this.totalPrice = totalPrice;
        }
    }

    public TransactionInfoDto(TransactionInfoDomain domain) {
        this.transactionId = domain.getTransactionId();
        this.idToken = domain.getIdToken();
        this.vehicleNo = domain.getVehicleNo();
        this.chargeSummary = new ChargeSummary(
                domain.getStartedTime(),
                domain.getEndedTime(),
                domain.getTotalMeterValue(),
                domain.getTotalPrice()
        );
    }
}