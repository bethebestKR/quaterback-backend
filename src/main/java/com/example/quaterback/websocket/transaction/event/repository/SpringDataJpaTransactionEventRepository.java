package com.example.quaterback.websocket.transaction.event.repository;

import com.example.quaterback.websocket.transaction.event.entity.TransactionEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaTransactionEventRepository extends JpaRepository<TransactionEventEntity, Long> {

}
