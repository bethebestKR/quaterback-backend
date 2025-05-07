package com.example.quaterback.api.domain.txinfo.repository;

import com.example.quaterback.api.domain.txinfo.entity.TransactionInfoEntity;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SpringDataJpaTxInfoRepository extends JpaRepository<TransactionInfoEntity, Long> {
    Optional<TransactionInfoEntity> findByTransactionId(String transactionId);

    @Query("""
    SELECT t FROM TransactionInfoEntity t
    WHERE t.evseId.id = :evseId
      AND (
        (t.startedTime BETWEEN :from AND :to)
        OR (t.endedTime BETWEEN :from AND :to)
        OR (t.startedTime <= :from AND t.endedTime >= :to)
      )
""")
    List<TransactionInfoEntity> findByChargerPkAndTimeRange(
            @Param("evseId") Long chargerPk,
            @Param("from") LocalDateTime start,
            @Param("to") LocalDateTime end);

    @Query("""
        SELECT t FROM TransactionInfoEntity t
        WHERE t.evseId.station.stationId = :stationId
        AND t.startedTime BETWEEN :start AND :end
    """)
    Page<TransactionInfoEntity> findByStationIdAndPeriod(
            @Param("stationId") String stationId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            Pageable pageable
    );
}
