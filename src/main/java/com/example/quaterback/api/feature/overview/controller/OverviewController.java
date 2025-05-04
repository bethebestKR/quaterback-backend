package com.example.quaterback.api.feature.overview.controller;

import com.example.quaterback.api.domain.station.service.StationService;
import com.example.quaterback.api.feature.overview.dto.response.StationOverviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/overview")
public class OverviewController {

    private final StationService stationService;

    @GetMapping("/stations")
    public List<StationOverviewResponse> getAllStationOverviews() {
        return stationService.getStationOverviews();
    }

    @GetMapping("/stations/{stationName}")
    public StationOverviewResponse getAllStationOverview(
            @PathVariable(name="stationName") String stationName
    ) {
        return stationService.getStationOverview(stationName);
    }
}
