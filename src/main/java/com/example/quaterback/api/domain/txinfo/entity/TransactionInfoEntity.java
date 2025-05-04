package com.example.quaterback.api.domain.txinfo.entity;

import com.example.quaterback.api.domain.txinfo.domain.TransactionInfoDomain;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tx_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionId;
    private LocalDateTime startedTime;
    private LocalDateTime endedTime;
    private String vehicleNo;
    private String userId;
    private String stationId;
    private Integer evseId;
    private Integer totalMeterValue;
    private Integer totalPrice;

    public static TransactionInfoEntity fromTransactionInfoDomain(TransactionInfoDomain domain) {
        return TransactionInfoEntity.builder()
                .transactionId(domain.getTransactionId())
                .startedTime(domain.getStartedTime())
                .vehicleNo(domain.getVehicleNo())
                .userId(domain.getUserId())
                .stationId(domain.getStationId())
                .evseId(domain.getEvseId())
                .build();
    }

    public static TransactionInfoDomain toDomain(TransactionInfoEntity entity) {
        return TransactionInfoDomain.builder()
                .transactionId(entity.getTransactionId())
                .startedTime(entity.getStartedTime())
                .endedTime(entity.getEndedTime())
                .vehicleNo(entity.getVehicleNo())
                .userId(entity.getUserId())
                .stationId(entity.getStationId())
                .evseId(entity.getEvseId())
                .totalMeterValue(entity.getTotalMeterValue())
                .totalPrice(entity.getTotalPrice())
                .build();
    }
    public String updateEndTimeAndTotalValues(TransactionInfoDomain domain) {
        endedTime = domain.getEndedTime();
        totalMeterValue = domain.getTotalMeterValue();
        totalPrice = domain.getTotalPrice();
        return transactionId;
    }
}
