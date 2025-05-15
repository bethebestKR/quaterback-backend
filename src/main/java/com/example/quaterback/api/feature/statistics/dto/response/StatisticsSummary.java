package com.example.quaterback.api.feature.statistics.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsSummary {
    private int totalChargingVolume;
    private int totalChargingCount;
    private int totalRevenue;
    private int averageChargingTime;
    private ComparisonWithPrevious comparisonWithPrevious;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ComparisonWithPrevious {
        private double volumeChangePercent;
        private double countChangePercent;
        private double revenueChangePercent;
        private double timeChangePercent;
    }
}
