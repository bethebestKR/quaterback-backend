package com.example.quaterback.api.feature.monitoring.facade;

import com.example.quaterback.api.domain.charger.service.ChargerService;
import com.example.quaterback.api.domain.station.domain.ChargingStationDomain;
import com.example.quaterback.api.domain.station.service.StationService;
import com.example.quaterback.api.domain.txinfo.domain.TransactionInfoDomain;
import com.example.quaterback.api.feature.dashboard.dto.response.StationFullInfoResponse;
import com.example.quaterback.api.feature.monitoring.dto.query.ChargingRecordQuery;
import com.example.quaterback.api.feature.monitoring.dto.query.DailyUsageQuery;
import com.example.quaterback.api.feature.monitoring.dto.response.*;

import com.example.quaterback.common.annotation.Facade;
import com.example.quaterback.websocket.transaction.event.service.TransactionEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

@Facade
@RequiredArgsConstructor
public class StationMonitoringFacade {

    private final TransactionEventService transactionEventService;
    private final StationService stationService;
    private final ChargerService chargerService;

    public ChargingRecordResponsePage getChargingHistory(String stationId, Pageable pageable) {
        ChargingStationDomain domain = stationService.getStation(stationId);
        Page<ChargingRecordQuery> pageQuery = transactionEventService.findChargingEventsByStationId(stationId, pageable);
        return ChargingRecordResponsePage.from(pageQuery,domain);
    }

    public List<HourlyCongestion> getCongestionChart(String stationId) {
        return transactionEventService.findHourlyCount(stationId);
    }

    public List<EvseIdResponse> getEvseIds(String stationId) {
        return chargerService.getEvesId(stationId);
    }

    public AvailableChargerPageResponse getAvailableChargerInfo(String stationId, Integer evseId, Pageable pageable) {
        Page<TransactionInfoDomain> domainPage = transactionEventService.findTransactionInfo(stationId,evseId, pageable);

        DailyUsageQuery today = transactionEventService.findOneDayUsageInfo( stationId, evseId, LocalDate.now());
        DailyUsageQuery yesterday = transactionEventService.findOneDayUsageInfo( stationId,evseId, LocalDate.now().minusDays(1));

        DailyUsageDto dailyUsage = DailyUsageDto.from(today, yesterday);
        return AvailableChargerPageResponse.from(dailyUsage,domainPage);
    }

    public UnavailableChargerPageResponse getUnavailableChargerInfo(String stationId, Integer evseId, Pageable pageable) {
        Page<TransactionInfoDomain> domainPage = transactionEventService.findTransactionInfo(stationId,evseId, pageable);
        return UnavailableChargerPageResponse.from(domainPage);
    }
}
