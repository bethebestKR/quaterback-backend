package com.example.quaterback.api.feature.dashboard.dto.response;

import java.time.LocalDateTime;

public record StationInfoResponse(
        String stationId,
        String name,
        String address,
        LocalDateTime registerDate,
        Long available,
        Long fault,
        Long unknown
) {}