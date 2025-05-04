package com.example.quaterback.api.feature.dashboard.facade;

import com.example.quaterback.api.domain.charger.service.ChargerService;
import com.example.quaterback.api.domain.station.service.StationService;
import com.example.quaterback.api.feature.dashboard.dto.response.*;
import com.example.quaterback.common.annotation.Facade;
import com.example.quaterback.websocket.transaction.event.service.TransactionEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Facade
@RequiredArgsConstructor
@Slf4j
public class DashboardFacade {

    private final TransactionEventService transactionEventService;
    private final ChargerService chargerService;
    private final StationService stationService;

    public List<HourlyDischargeResponse> getHourlyDischarge() {
        log.info("fuck2");
       return transactionEventService.getHourlyDischarge();
    }

    public DashboardSummaryResponse getSummary() {
        return transactionEventService.getDailySummary();
    }

    public List<ChargerUsageResponse> getChargerUsage() {
        return transactionEventService.getChargerUsage();
    }

    public List<StationFullInfoResponse> getStations() {
        return stationService.getStationInfos();
    }

    public StationFullInfoResponse getStation(String stationName) {
        return stationService.getStationInfo(stationName);
    }

    public DeleteResultResponse removeStation(String stationName) {
         return stationService.removeStation(stationName);
    }
}
