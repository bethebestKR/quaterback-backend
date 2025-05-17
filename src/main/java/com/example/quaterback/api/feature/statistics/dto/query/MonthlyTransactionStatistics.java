package com.example.quaterback.api.feature.statistics.dto.query;


public interface MonthlyTransactionStatistics {
    Double getTotalChargingVolume();
    Long getTotalChargingCount();
    Double getTotalRevenue();
    Double getAverageChargingTime(); // 초 단위 평균
}