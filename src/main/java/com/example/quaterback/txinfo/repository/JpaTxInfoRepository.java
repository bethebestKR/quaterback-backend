package com.example.quaterback.txinfo.repository;

import com.example.quaterback.txinfo.domain.TransactionInfoDomain;
import com.example.quaterback.txinfo.entity.TransactionInfoEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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

        entity.setEndedTime(domain.getEndedTime());
        entity.setTotalMeterValue(domain.getTotalMeterValue());
        entity.setTotalPrice(domain.getTotalPrice());
        return null;
    }
}
