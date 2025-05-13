package com.example.quaterback.api.feature.overview.controller;

import com.example.quaterback.api.domain.charger.service.ChargerService;
import com.example.quaterback.api.domain.station.service.StationService;
import com.example.quaterback.api.feature.managing.dto.apiResponse.ApiResponse;
import com.example.quaterback.common.exception.StationIdValidator;
import com.example.quaterback.api.feature.overview.dto.response.CsAndChargerParams;
import com.example.quaterback.api.feature.overview.dto.response.StationOverviewResponse;
import com.example.quaterback.api.feature.overview.service.OverviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/overview")
public class OverviewController {

    private final StationService stationService;
    private final ChargerService chargerService;
    private final OverviewService overviewService;

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

    @PostMapping("/station/create")
    public ResponseEntity<ApiResponse<String>> createCs(
        @RequestBody CsAndChargerParams request
    ){
        StationIdValidator.validateStationId(request.getStationId());
        overviewService.createCsAndCharger(request);
        return ResponseEntity.ok(new ApiResponse<>("success", "created"));
    }
}
