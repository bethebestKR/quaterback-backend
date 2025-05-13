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
    Double totalChargedEnergy;
    Long totalVehicleCount;
    Double totalRevenue;

    //퍼센트계산
    int chargedEnergyDiffPercent;
    int vehicleCountDiffPercent;
    int revenueDiffPercent;

    public static DailyUsageDto from(DailyUsageQuery today, DailyUsageQuery yesterday) {
        double todayEnergy = today != null && today.getTotalChargedEnergy() != null ? today.getTotalChargedEnergy() : 0.0;
        double todayRevenue = today != null && today.getTotalRevenue() != null ? today.getTotalRevenue() : 0.0;
        long todayCount = today != null && today.getTotalVehicleCount() != null ? today.getTotalVehicleCount() : 0;

        double yesterdayEnergy = yesterday != null && yesterday.getTotalChargedEnergy() != null ? yesterday.getTotalChargedEnergy() : 0L;
        double yesterdayRevenue = yesterday != null && yesterday.getTotalRevenue() != null ? yesterday.getTotalRevenue() : 0L;
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

    private static int calculateDiffPercent(double today, double yesterday) {
        if (yesterday == 0) return today == 0 ? 0 : 100;
        return (int) (((today - yesterday) / yesterday) * 100);
    }
}
