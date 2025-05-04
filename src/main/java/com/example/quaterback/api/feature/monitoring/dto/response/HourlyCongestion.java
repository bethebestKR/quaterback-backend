package com.example.quaterback.api.feature.monitoring.dto.response;

public record HourlyCongestion(
        int hour,
        Long count,
        boolean isPeak
) {
}