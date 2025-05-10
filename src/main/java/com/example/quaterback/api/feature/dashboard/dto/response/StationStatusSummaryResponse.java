package com.example.quaterback.api.feature.dashboard.dto.response;

public record StationStatusSummaryResponse(
        int available,
        int fault,
        int unknown
) {}