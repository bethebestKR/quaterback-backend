package com.example.quaterback.api.domain.txlog.repository;

import com.example.quaterback.api.domain.txlog.domain.TransactionLogDomain;
import com.example.quaterback.api.domain.txlog.entity.TransactionLogEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JpaTxLogRepository implements TxLogRepository {

    private final SpringDataJpaTxLogRepository springDataJpaTxLogRepository;

    @Override
    public String save(TransactionLogDomain domain) {
        TransactionLogEntity entity = TransactionLogEntity.fromTxLogDomain(domain);
        springDataJpaTxLogRepository.save(entity);
        return entity.getTransactionId();
    }

    @Override
    public Integer getTotalMeterValue(String transactionId) {
        Integer totalMeterValue = springDataJpaTxLogRepository.avgMeterValueByTransactionId(transactionId);
        return totalMeterValue;
    }


}
