package com.example.quaterback.api.feature.monitoring.dto.response;

import java.util.List;

public record CongestionChartResponse(
        String stationId,
        List<HourlyCongestion> congestionByHour
) {

}