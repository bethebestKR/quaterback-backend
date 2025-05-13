package com.example.quaterback.api.feature.managing.dto.txInfo;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class TransactionSummaryDto {
    private Double totalMeterValue;
    private Double totalPrice;
}