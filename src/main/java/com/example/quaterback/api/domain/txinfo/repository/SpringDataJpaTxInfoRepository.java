package com.example.quaterback.api.domain.txinfo.repository;

import com.example.quaterback.api.domain.txinfo.entity.TransactionInfoEntity;
import com.example.quaterback.api.feature.dashboard.dto.query.ChargerUsageQuery;
import com.example.quaterback.api.feature.dashboard.dto.query.DashboardSummaryQuery;
import com.example.quaterback.api.feature.dashboard.dto.response.ChargerUsageResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SpringDataJpaTxInfoRepository extends JpaRepository<TransactionInfoEntity, Long> {
    Optional<TransactionInfoEntity> findByTransactionId(String transactionId);

    @Query("""
    SELECT FUNCTION('HOUR', t.startedTime), SUM(t.totalMeterValue)
    FROM TransactionInfoEntity t
    WHERE t.totalMeterValue IS NOT NULL
    GROUP BY FUNCTION('HOUR', t.startedTime)
    ORDER BY FUNCTION('HOUR', t.startedTime)
""")
    List<Object[]> findTotalDischargePerHour();

    @Query("""
    SELECT new com.example.quaterback.api.feature.dashboard.dto.query.DashboardSummaryQuery(
        COUNT(t), COALESCE(SUM(t.totalPrice), 0), COALESCE(SUM(t.totalMeterValue), 0)
    )
    FROM TransactionInfoEntity t
""")
    DashboardSummaryQuery findDashboardSummary();

    @Query("""
    SELECT new com.example.quaterback.api.feature.dashboard.dto.query.ChargerUsageQuery(
        t.startedTime,
        cs.address,
        cs.model,
        t.totalMeterValue,
        t.totalPrice,
        t.transactionId
    )
    FROM TransactionInfoEntity t
    JOIN ChargingStationEntity cs ON t.stationId = cs.stationId
    ORDER BY t.startedTime DESC
""")
    List<ChargerUsageQuery> findWithStationInfo();

}
