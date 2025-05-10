package com.example.quaterback.api.feature.monitoring.dto.response;

import com.example.quaterback.api.feature.monitoring.dto.query.DailyUsageQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyUsageDto {
    //일일정보
    Long totalChargedEnergy;
    Long totalVehicleCount;
    Long totalRevenue;

    //퍼센트계산
    int chargedEnergyDiffPercent;
    int vehicleCountDiffPercent;
    int revenueDiffPercent;

    public static DailyUsageDto from(DailyUsageQuery today, DailyUsageQuery yesterday) {
        long todayEnergy = today != null && today.getTotalChargedEnergy() != null ? today.getTotalChargedEnergy() : 0L;
        long todayRevenue = today != null && today.getTotalRevenue() != null ? today.getTotalRevenue() : 0L;
        long todayCount = today != null && today.getTotalVehicleCount() != null ? today.getTotalVehicleCount() : 0;

        long yesterdayEnergy = yesterday != null && yesterday.getTotalChargedEnergy() != null ? yesterday.getTotalChargedEnergy() : 0L;
        long yesterdayRevenue = yesterday != null && yesterday.getTotalRevenue() != null ? yesterday.getTotalRevenue() : 0L;
        long yesterdayCount = yesterday != null && yesterday.getTotalVehicleCount() != null ? yesterday.getTotalVehicleCount() : 0;

        return DailyUsageDto.builder()
                .totalChargedEnergy(todayEnergy)
                .totalRevenue(todayRevenue)
                .totalVehicleCount((long) todayCount)
                .chargedEnergyDiffPercent(calculateDiffPercent(todayEnergy, yesterdayEnergy))
                .revenueDiffPercent(calculateDiffPercent(todayRevenue, yesterdayRevenue))
                .vehicleCountDiffPercent(calculateDiffPercent(todayCount, yesterdayCount))
                .build();
    }

    private static int calculateDiffPercent(long today, long yesterday) {
        if (yesterday == 0) return today == 0 ? 0 : 100;
        return (int) (((double) (today - yesterday) / yesterday) * 100);
    }
}
