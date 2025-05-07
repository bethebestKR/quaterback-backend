package com.example.quaterback.api.domain.txinfo.repository;

import com.example.quaterback.api.domain.charger.entity.ChargerEntity;
import com.example.quaterback.api.domain.charger.repository.SpringDataJpaChargerRepository;
import com.example.quaterback.api.domain.txinfo.domain.TransactionInfoDomain;
import com.example.quaterback.api.domain.txinfo.entity.TransactionInfoEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JpaTxInfoRepository implements TxInfoRepository {

    private final SpringDataJpaTxInfoRepository springDataJpaTxInfoRepository;
    private final SpringDataJpaChargerRepository springDataJpaChargerRepository;

    @Override
    public String save(TransactionInfoDomain domain) {
        String stationId = domain.getStationId();
        Integer evseId = domain.getEvseId();

        ChargerEntity chargerEntity = springDataJpaChargerRepository.findByStation_StationIdAndEvseId(stationId, evseId)
                .orElseThrow(() -> new EntityNotFoundException("entity not found"));

        TransactionInfoEntity entity = TransactionInfoEntity.fromTransactionInfoDomain(domain, chargerEntity);
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
    public List<TransactionInfoDomain> findByChargerPkAndCreatedAtBetween(TransactionInfoDomain domain,
                                                                          Long chargerPk) {
        LocalDateTime start = domain.getStartedTime();
        LocalDateTime end = domain.getEndedTime();

        List<TransactionInfoEntity> txEntities = springDataJpaTxInfoRepository.findByChargerPkAndTimeRange(
                chargerPk
                ,start
                ,end);
        List<TransactionInfoDomain> txDomains = txEntities.stream()
                .map(TransactionInfoDomain::fromTxEntityDomain)
                .collect(Collectors.toList());
        return txDomains;
    }

    @Override
    public Page<TransactionInfoDomain> findByStationIdAndCreatedAtBetween(TransactionInfoDomain domain,
                                                                          String stationId,
                                                                          Pageable pageable) {
        LocalDateTime start = domain.getStartedTime();
        LocalDateTime end = domain.getEndedTime();

        Page<TransactionInfoEntity> txInfoEntities =  springDataJpaTxInfoRepository.findByStationIdAndPeriod(
                stationId, start, end, pageable
        );

        Page<TransactionInfoDomain> txInfoDomains = txInfoEntities
                .map(entity -> TransactionInfoDomain.fromTxEntityDomain(entity));
        return txInfoDomains;
    }

    @Override
    public TransactionInfoDomain getOneTxInfoByTxId(TransactionInfoDomain txInfoDomain) {
        TransactionInfoEntity txEntity = springDataJpaTxInfoRepository.findByTransactionId(txInfoDomain.getTransactionId())
                .orElseThrow(() -> new EntityNotFoundException("tx info entity not found"));
        return TransactionInfoDomain.fromTxEntityDomain(txEntity);
    }


}
