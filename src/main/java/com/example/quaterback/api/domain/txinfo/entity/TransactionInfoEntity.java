package com.example.quaterback.api.domain.txinfo.entity;

import com.example.quaterback.api.domain.charger.entity.ChargerEntity;
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

    @Column(unique = true, nullable = false)
    private String transactionId;
    private LocalDateTime startedTime;
    private LocalDateTime endedTime;
    private String vehicleNo;
    private String idToken;
    private String stationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evse_id")
    private ChargerEntity evseId;

    private Double totalMeterValue;
    private Double totalPrice;

    private String errorCode;

    public static TransactionInfoEntity fromTransactionInfoDomain(TransactionInfoDomain domain, ChargerEntity chargerEntity) {
        return TransactionInfoEntity.builder()
                .transactionId(domain.getTransactionId())
                .startedTime(domain.getStartedTime())
                .endedTime(domain.getEndedTime())
                .vehicleNo(domain.getVehicleNo())
                .stationId(domain.getStationId())
                .totalPrice(domain.getTotalPrice())
                .totalMeterValue(domain.getTotalMeterValue())
                .evseId(chargerEntity)
                .idToken(domain.getIdToken())
                .errorCode(domain.getErrorCode())
                .build();
    }

    public String updateEndTimeAndTotalValues(TransactionInfoDomain domain) {
        endedTime = domain.getEndedTime();
        totalMeterValue = domain.getTotalMeterValue();
        totalPrice = domain.getTotalPrice();
        errorCode = domain.getErrorCode();
        return transactionId;
    }



    public TransactionInfoDomain toDomain() {
        return TransactionInfoDomain.builder()
                .transactionId(transactionId)
                .startedTime(startedTime)
                .endedTime(endedTime)
                .vehicleNo(vehicleNo)
                .totalMeterValue(totalMeterValue)
                .totalPrice(totalPrice)
                .stationId(stationId)
                .idToken(idToken)
                .errorCode(errorCode)
                .build();
    }
}
