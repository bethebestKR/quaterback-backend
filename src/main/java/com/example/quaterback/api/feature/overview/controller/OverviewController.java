package com.example.quaterback.api.feature.overview.controller;

import com.example.quaterback.api.domain.station.service.StationService;
import com.example.quaterback.api.feature.overview.dto.response.StationOverviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
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
