package com.example.quaterback.api.domain.txinfo.repository;

import com.example.quaterback.api.domain.txinfo.entity.TransactionInfoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface SpringDataJpaTxInfoRepository extends JpaRepository<TransactionInfoEntity, Long> {
    Optional<TransactionInfoEntity> findByTransactionId(String transactionId);
    Page<TransactionInfoEntity> findByIdTokenOrderByStartedTimeDesc(String idToken, Pageable pageable);

    @Query("select t from TransactionInfoEntity t " +
           "where t.idToken = :idToken " +
           "and date(t.startedTime) between :start and :end " +
           "order by t.startedTime desc")
    Page<TransactionInfoEntity> findByIdTokenAndStartedTimeBetweenOrderByStartedTimeDesc(
            @Param("idToken") String idToken,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end,
            Pageable pageable);
}
