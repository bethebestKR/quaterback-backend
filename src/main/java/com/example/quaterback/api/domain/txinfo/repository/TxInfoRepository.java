package com.example.quaterback.api.domain.txinfo.repository;

import com.example.quaterback.api.domain.txinfo.domain.TransactionInfoDomain;
import com.example.quaterback.api.feature.dashboard.dto.query.ChargerUsageQuery;
import com.example.quaterback.api.feature.dashboard.dto.query.DashboardSummaryQuery;
import com.example.quaterback.api.feature.dashboard.dto.response.ChargerUsageResponse;
import com.example.quaterback.api.feature.monitoring.dto.query.ChargingRecordQuery;
import com.example.quaterback.api.feature.monitoring.dto.query.DailyUsageQuery;
import com.example.quaterback.api.feature.monitoring.dto.query.HourlyCongestionQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TxInfoRepository {
    String save(TransactionInfoDomain domain);

    String updateEndTime(TransactionInfoDomain domain);

    List<Object[]> findTotalDischargePerHour();

    DashboardSummaryQuery findDashboardSummary();

    List<ChargerUsageQuery> findWithStationInfo();

    Page<ChargingRecordQuery> findChargerUsageByStationId(String stationId, Pageable pageable);

    List<HourlyCongestionQuery> findHourlyCountsByStationId(String stationId);

    Page<TransactionInfoDomain> findAllByEvseId(Integer evseId, Pageable pageable);

   DailyUsageQuery findDailyUsageByEvseIdAndDate(Integer evseId, LocalDate date);
}
