package com.example.quaterback.txinfo.repository;

import com.example.quaterback.txinfo.domain.TransactionInfoDomain;

public interface TxInfoRepository {
    String save(TransactionInfoDomain domain);
    String updateEndTime(TransactionInfoDomain domain);
}
