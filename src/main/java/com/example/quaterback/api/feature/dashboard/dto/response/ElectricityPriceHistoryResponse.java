package com.example.quaterback.api.feature.dashboard.dto.response;

public record ElectricityPriceHistoryResponse(
        String timestamp,
        int price
) {}