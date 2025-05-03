package com.example.quaterback.api.domain.txinfo.repository;

import com.example.quaterback.api.domain.txinfo.domain.TransactionInfoDomain;

public interface TxInfoRepository {
    String save(TransactionInfoDomain domain);
    String updateEndTime(TransactionInfoDomain domain);
}
