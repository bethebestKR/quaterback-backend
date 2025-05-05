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

import java.time.LocalDate;

@RequiredArgsConstructor
@Repository
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
    public Page<TransactionInfoDomain> findByIdTokenOrderByStartedTimeDesc(String idToken, Pageable pageable) {
        Page<TransactionInfoEntity> entityPage = springDataJpaTxInfoRepository.findByIdTokenOrderByStartedTimeDesc(idToken, pageable);
        return entityPage.map(TransactionInfoEntity::toDomain);
    }

    @Override
    public Page<TransactionInfoDomain> findByIdTokenAndStartedTimeBetweenOrderByStartedTimeDesc(String idToken, LocalDate startTime, LocalDate endTime, Pageable pageable) {
        Page<TransactionInfoEntity> entityPage = springDataJpaTxInfoRepository.findByIdTokenAndStartedTimeBetweenOrderByStartedTimeDesc(idToken, startTime, endTime, pageable);
        return entityPage.map(TransactionInfoEntity::toDomain);
    }

}
