package com.example.quaterback.api.domain.txinfo.repository;

import com.example.quaterback.api.domain.txinfo.domain.TransactionInfoDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface TxInfoRepository {
    String save(TransactionInfoDomain domain);
    String updateEndTime(TransactionInfoDomain domain);
    Page<TransactionInfoDomain> findByIdTokenOrderByStartedTimeDesc(String idToken, Pageable pageable);
    Page<TransactionInfoDomain> findByIdTokenAndStartedTimeBetweenOrderByStartedTimeDesc(String idToken, LocalDate startTime, LocalDate endTime, Pageable pageable);
}
