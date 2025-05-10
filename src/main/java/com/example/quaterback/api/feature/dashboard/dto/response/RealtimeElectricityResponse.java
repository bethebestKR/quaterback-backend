package com.example.quaterback.api.feature.dashboard.dto.response;

public record RealtimeElectricityResponse(
        String company,
        int price,
        double changeRate
) {}