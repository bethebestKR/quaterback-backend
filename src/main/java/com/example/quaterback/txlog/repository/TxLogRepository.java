package com.example.quaterback.txlog.repository;

import com.example.quaterback.txlog.domain.TransactionLogDomain;

public interface TxLogRepository {
    String save(TransactionLogDomain domain);
    Integer getTotalMeterValue(String transactionId);
}
