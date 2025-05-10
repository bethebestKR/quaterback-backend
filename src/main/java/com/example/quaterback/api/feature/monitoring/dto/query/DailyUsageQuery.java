package com.example.quaterback.api.feature.monitoring.dto.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyUsageQuery {
    private Long totalChargedEnergy;
    private Long totalVehicleCount;
    private Long totalRevenue;
}
