package com.example.quaterback.websocket.transaction.event.repository;

import com.example.quaterback.websocket.transaction.event.domain.TransactionEventDomain;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionEventRepository {

    Long save(TransactionEventDomain transactionEventDomain);
}
