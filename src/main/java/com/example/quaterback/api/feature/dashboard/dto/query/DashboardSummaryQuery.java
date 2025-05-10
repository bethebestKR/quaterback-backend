package com.example.quaterback.api.feature.dashboard.dto.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class DashboardSummaryQuery {
    private long usage;
    private long totalProfit;
    private long totalDischarge;

    public DashboardSummaryQuery(long usage, long totalProfit, long totalDischarge) {
        this.usage = usage;
        this.totalProfit = totalProfit;
        this.totalDischarge = totalDischarge;
    }
}