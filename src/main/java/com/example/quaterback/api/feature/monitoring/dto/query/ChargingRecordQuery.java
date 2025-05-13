package com.example.quaterback.api.feature.monitoring.dto.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChargingRecordQuery {
    private LocalDateTime startedTime;
    private LocalDateTime endedTime;
    private Double price;
    private String transactionId;
}
