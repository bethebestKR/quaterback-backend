package com.example.quaterback.api.feature.monitoring.facade;

import com.example.quaterback.api.domain.station.domain.ChargingStationDomain;
import com.example.quaterback.api.domain.station.service.StationService;
import com.example.quaterback.api.feature.dashboard.dto.response.StationFullInfoResponse;
import com.example.quaterback.api.feature.monitoring.dto.query.ChargingRecordQuery;
import com.example.quaterback.api.feature.monitoring.dto.response.ChargingRecordResponse;
import com.example.quaterback.api.feature.monitoring.dto.response.ChargingRecordResponsePage;
import com.example.quaterback.api.feature.monitoring.dto.response.HourlyCongestion;
import com.example.quaterback.common.annotation.Facade;
import com.example.quaterback.websocket.transaction.event.service.TransactionEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Facade
@RequiredArgsConstructor
public class StationMonitoringFacade {

    private final TransactionEventService transactionEventService;
    private final StationService stationService;

    public ChargingRecordResponsePage getChargingHistory(String stationId, Pageable pageable) {
        ChargingStationDomain domain = stationService.getStation(stationId);
        Page<ChargingRecordQuery> pageQuery = transactionEventService.findChargingEventsByStationId(stationId, pageable);
        return ChargingRecordResponsePage.from(pageQuery,domain);
    }

    public List<HourlyCongestion> getCongestionChart(String stationId) {
        return transactionEventService.findHourlyCount(stationId);
    }
}
