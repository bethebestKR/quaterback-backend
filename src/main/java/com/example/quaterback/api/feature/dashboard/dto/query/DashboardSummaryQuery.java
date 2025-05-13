package com.example.quaterback.api.feature.dashboard.dto.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class DashboardSummaryQuery {
    private long usage;
    private double totalProfit;
    private double totalDischarge;

    public DashboardSummaryQuery(long usage, double totalProfit, double totalDischarge) {
        this.usage = usage;
        this.totalProfit = totalProfit;
        this.totalDischarge = totalDischarge;
    }
}