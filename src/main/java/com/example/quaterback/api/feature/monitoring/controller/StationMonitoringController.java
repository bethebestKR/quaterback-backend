package com.example.quaterback.api.feature.monitoring.controller;

import com.example.quaterback.api.feature.monitoring.dto.response.*;
import com.example.quaterback.api.feature.monitoring.facade.StationMonitoringFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/monitoring")
public class StationMonitoringController {

    private final StationMonitoringFacade stationMonitoringFacade;

    @GetMapping("/charging-history/{stationId}")
    public ChargingRecordResponsePage getChargingHistoryPaged(
            @PathVariable(name = "stationId") String stationId,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name="size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("endedTime").descending());
        return stationMonitoringFacade.getChargingHistory(stationId, pageable);
    }

    @GetMapping("/congestion/{stationId}")
    public List<HourlyCongestion> getHourlyCongestion(
            @PathVariable(name = "stationId") String stationId
    ) {
        return stationMonitoringFacade.getCongestionChart(stationId);
    }
    @GetMapping("/evse-ids/{stationId}")
    public List<EvseIdResponse> getEvseIds(
            @PathVariable(name="stationId") String stationId
    ){
        return stationMonitoringFacade.getEvseIds(stationId);
    }

    @GetMapping("/charger-info/available/{evseId}")
    public AvailableChargerPageResponse getAvailableChargerInfo(
            @PathVariable(name="evseId") Integer evseId,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name ="size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("endedTime").descending());
        return stationMonitoringFacade.getAvailableChargerInfo(evseId, pageable);
    }

    @GetMapping("/charger-info/unavailable/{evseId}")
    public UnavailableChargerPageResponse getUnavailableChargerInfo(
            @PathVariable(name="evseId") Integer evseId,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name ="size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("endedTime").descending());
        return stationMonitoringFacade.getUnavailableChargerInfo(evseId, pageable);
    }

    //todo 충전요금 추후 계발
    @GetMapping("/charger-money/{stationId}")
    public Long todoCharger() {
        return 1L;
    }
}
