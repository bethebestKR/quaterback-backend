package com.example.quaterback.api.feature.managing.dto.txInfo;

import com.example.quaterback.api.domain.txinfo.domain.TransactionInfoDomain;
import com.example.quaterback.api.domain.txinfo.entity.TransactionInfoEntity;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
public class TransactionInfoDto {

    private final String transactionId;
    private final String userId;
    private final String vehicleNo;
    private final ChargeSummary chargeSummary;

    @Data
    public static class ChargeSummary {
        private final LocalDateTime startedTime;
        private final LocalDateTime endedTime;
        private final Integer totalMeterValue;
        private final Integer totalPrice;

        public ChargeSummary(LocalDateTime startedTime, LocalDateTime endedTime, Integer totalMeterValue, Integer totalPrice) {
            this.startedTime = startedTime;
            this.endedTime = endedTime;
            this.totalMeterValue = totalMeterValue;
            this.totalPrice = totalPrice;
        }
    }

    public TransactionInfoDto(TransactionInfoDomain domain) {
        this.transactionId = domain.getTransactionId();
        this.userId = domain.getUserId();
        this.vehicleNo = domain.getVehicleNo();
        this.chargeSummary = new ChargeSummary(
                domain.getStartedTime(),
                domain.getEndedTime(),
                domain.getTotalMeterValue(),
                domain.getTotalPrice()
        );
    }
}