package com.example.quaterback.api.feature.managing.dto.txInfo;

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

    public TransactionInfoDto(TransactionInfoEntity entity) {
        this.transactionId = entity.getTransactionId();
        this.userId = entity.getUserId();
        this.vehicleNo = entity.getVehicleNo();
        this.chargeSummary = new ChargeSummary(
                entity.getStartedTime(),
                entity.getEndedTime(),
                entity.getTotalMeterValue(),
                entity.getTotalPrice()
        );
    }
}