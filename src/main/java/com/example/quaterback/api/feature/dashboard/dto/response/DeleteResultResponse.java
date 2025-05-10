package com.example.quaterback.api.feature.dashboard.dto.response;

import lombok.Data;

@Data
public class DeleteResultResponse {
    private final String stationName;
    private final String message;

    public DeleteResultResponse(String stationName, String message) {
        this.stationName = stationName;
        this.message = message;
    }
}
