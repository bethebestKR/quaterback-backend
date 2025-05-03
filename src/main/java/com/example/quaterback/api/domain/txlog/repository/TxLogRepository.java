package com.example.quaterback.api.domain.txlog.repository;

import com.example.quaterback.api.domain.txlog.domain.TransactionLogDomain;

public interface TxLogRepository {
    String save(TransactionLogDomain domain);
    Integer getTotalMeterValue(String transactionId);
}
