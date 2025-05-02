package com.example.quaterback.txlog.repository;

import com.example.quaterback.txlog.domain.TransactionLogDomain;
import com.example.quaterback.txlog.entity.TransactionLogEntity;
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
        Integer totalMeterValue = springDataJpaTxLogRepository.sumMeterValueByTransactionId(transactionId);
        return totalMeterValue;
    }


}
