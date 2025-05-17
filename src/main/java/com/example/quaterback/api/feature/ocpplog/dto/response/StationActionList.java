package com.example.quaterback.api.feature.ocpplog.dto.response;

import java.util.List;

public record StationActionList(
        List<String> stationList,
        List<String> actionList
) {
}
