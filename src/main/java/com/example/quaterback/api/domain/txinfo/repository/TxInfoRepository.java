package com.example.quaterback.api.domain.txinfo.repository;

import com.example.quaterback.api.domain.txinfo.domain.TransactionInfoDomain;
import com.example.quaterback.api.domain.txinfo.entity.TransactionInfoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface TxInfoRepository {
    String save(TransactionInfoDomain domain);

    String updateEndTime(TransactionInfoDomain domain);

    List<TransactionInfoEntity> findByChargerPkAndCreatedAtBetween(LocalDateTime start,
                                                                   LocalDateTime end,
                                                                   Long chargerPk);

    Page<TransactionInfoEntity> findByStationIdAndCreatedAtBetween(LocalDateTime start,
                                                                   LocalDateTime end,
                                                                   String stationId,
                                                                   Pageable pageable);

    TransactionInfoDomain getOneTxInfoByTxId(TransactionInfoDomain txInfoDomain);

}
