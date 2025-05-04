package com.example.quaterback.api.domain.txinfo.repository;

import com.example.quaterback.api.domain.txinfo.domain.TransactionInfoDomain;
import com.example.quaterback.api.domain.txinfo.entity.TransactionInfoEntity;
import com.example.quaterback.api.feature.dashboard.dto.query.ChargerUsageQuery;
import com.example.quaterback.api.feature.dashboard.dto.query.DashboardSummaryQuery;
import com.example.quaterback.api.feature.dashboard.dto.response.ChargerUsageResponse;
import com.example.quaterback.api.feature.monitoring.dto.query.ChargingRecordQuery;
import com.example.quaterback.api.feature.monitoring.dto.query.HourlyCongestionQuery;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class JpaTxInfoRepository implements TxInfoRepository {

    private final SpringDataJpaTxInfoRepository springDataJpaTxInfoRepository;

    @Override
    public String save(TransactionInfoDomain domain) {
        TransactionInfoEntity entity = TransactionInfoEntity.fromTransactionInfoDomain(domain);
        springDataJpaTxInfoRepository.save(entity);
        return entity.getTransactionId();
    }

    @Override
    public String updateEndTime(TransactionInfoDomain domain) {
        TransactionInfoEntity entity = springDataJpaTxInfoRepository.findByTransactionId(domain.getTransactionId())
                .orElseThrow(() -> new EntityNotFoundException("tx info entity not found"));

        if (domain.getEndedTime() != null && domain.getTotalMeterValue() != null && domain.getTotalPrice() != null) {
            String returnTxId = entity.updateEndTimeAndTotalValues(domain);
            return returnTxId;
        }
        return null;
    }

    @Override
    public List<Object[]> findTotalDischargePerHour() {
        return springDataJpaTxInfoRepository.findTotalDischargePerHour();
    }

    @Override
    public DashboardSummaryQuery findDashboardSummary() {
        return springDataJpaTxInfoRepository.findDashboardSummary();
    }

    @Override
    public List<ChargerUsageQuery> findWithStationInfo() {
        return springDataJpaTxInfoRepository.findWithStationInfo();
    }

    @Override
    public Page<ChargingRecordQuery> findChargerUsageByStationId(String stationId, Pageable pageable) {
        return springDataJpaTxInfoRepository.findChargerUsageByStationId(stationId, pageable);
    }

    @Override
    public List<HourlyCongestionQuery> findHourlyCountsByStationId(String stationId) {
        return springDataJpaTxInfoRepository.findHourlyCountsByStationId(stationId);
    }
}
