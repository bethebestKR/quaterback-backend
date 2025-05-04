package com.example.quaterback.api.feature.dashboard.dto.response;

import com.example.quaterback.api.feature.dashboard.dto.query.DashboardSummaryQuery;
import lombok.Builder;

@Builder
public record DashboardSummaryResponse(Long usage, Long profit, Long discharge) {

    public static DashboardSummaryResponse from(DashboardSummaryQuery query){
        return DashboardSummaryResponse.builder()
                .usage(query.getUsage())
                .profit(query.getTotalProfit())
                .discharge(query.getTotalDischarge())
                .build();
    }
}
