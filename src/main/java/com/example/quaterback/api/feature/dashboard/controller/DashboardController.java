package com.example.quaterback.api.feature.dashboard.controller;

import com.example.quaterback.api.feature.dashboard.dto.request.StationAddRequest;
import com.example.quaterback.api.feature.dashboard.dto.response.*;
import com.example.quaterback.api.feature.dashboard.facade.DashboardFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
@Slf4j
public class DashboardController {

    private final DashboardFacade dashboardFacade;

    @GetMapping("/discharge-by-hour")
    public List<HourlyDischargeResponse> getDischargeByHour() {
        return dashboardFacade.getHourlyDischarge();
    }

    @GetMapping("/summary")
    public DashboardSummaryResponse getDashboardSummary() {
        return dashboardFacade.getSummary();
    }

    @GetMapping("/chargers/usage")
    public List<ChargerUsageResponse> getChargerUsage() {
        return dashboardFacade.getChargerUsage();
    }

    //todo 계빨예정
    @GetMapping("/electricity-price/realtime")
    public Long getRealtimeElectricityPrice() {
        return 1L;
    }
    //todo 계빨예정
    @GetMapping("/electricity-price/history")
    public Long getElectricityPriceHistory() {
       return 1L;
    }

    @GetMapping("/stations")
    public List<StationFullInfoResponse> getStations() {
        return dashboardFacade.getStations();
    }

    @GetMapping("/stations/{stationName}")
    public StationFullInfoResponse getStation(@PathVariable(name = "stationName") String stationName) {
        return dashboardFacade.getStation(stationName);
    }

    @PostMapping("/stations")
    public Long addStation(@RequestBody StationAddRequest stationAddRequest) {
        return 1L;
        //추후 개발 예정
    }

    @DeleteMapping("/stations/{stationName}")
    public DeleteResultResponse deleteStation(@PathVariable(name = "stationName") String stationName) {
        return dashboardFacade.removeStation(stationName);
    }
}
