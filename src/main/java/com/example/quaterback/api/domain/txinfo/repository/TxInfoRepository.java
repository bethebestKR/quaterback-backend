package com.example.quaterback.api.domain.txinfo.repository;

import com.example.quaterback.api.domain.txinfo.domain.TransactionInfoDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import com.example.quaterback.api.feature.dashboard.dto.query.ChargerUsageQuery;
import com.example.quaterback.api.feature.dashboard.dto.query.DashboardSummaryQuery;
import com.example.quaterback.api.feature.monitoring.dto.query.ChargingRecordQuery;
import com.example.quaterback.api.feature.monitoring.dto.query.DailyUsageQuery;
import com.example.quaterback.api.feature.monitoring.dto.query.HourlyCongestionQuery;

import java.util.List;

public interface TxInfoRepository {
    String save(TransactionInfoDomain domain);

    String updateEndTime(TransactionInfoDomain domain);

    List<TransactionInfoDomain> findByChargerPkAndCreatedAtBetween(TransactionInfoDomain domain,
                                                                   Long chargerPk);

    Page<TransactionInfoDomain> findByStationIdAndCreatedAtBetween(TransactionInfoDomain domain,
                                                                   String stationId,
                                                                   Pageable pageable);

    TransactionInfoDomain getOneTxInfoByTxId(TransactionInfoDomain txInfoDomain);

    Page<TransactionInfoDomain> findByIdTokenOrderByStartedTimeDesc(String idToken, Pageable pageable);
    Page<TransactionInfoDomain> findByIdTokenAndStartedTimeBetweenOrderByStartedTimeDesc(String idToken, LocalDate startTime, LocalDate endTime, Pageable pageable);

    List<Object[]> findTotalDischargePerHour();

    DashboardSummaryQuery findDashboardSummary();

    List<ChargerUsageQuery> findWithStationInfo();

    Page<ChargingRecordQuery> findChargerUsageByStationId(String stationId, Pageable pageable);

    List<HourlyCongestionQuery> findHourlyCountsByStationId(String stationId);

    Page<TransactionInfoDomain> findAllByEvseId(String stationId, Integer evseId, Pageable pageable);

   DailyUsageQuery findDailyUsageByEvseIdAndDate(String stationId, Integer evseId, LocalDate date);
}
