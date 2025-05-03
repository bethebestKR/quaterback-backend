package com.example.quaterback.api.domain.txinfo.repository;

import com.example.quaterback.api.domain.txinfo.entity.TransactionInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaTxInfoRepository extends JpaRepository<TransactionInfoEntity, Long> {
    Optional<TransactionInfoEntity> findByTransactionId(String transactionId);
}
