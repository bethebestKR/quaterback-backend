package com.example.quaterback.websocket.transaction.event.repository;

import com.example.quaterback.websocket.transaction.event.domain.TransactionEventDomain;
import com.example.quaterback.websocket.transaction.event.entity.TransactionEventEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaTransactionEventRepository implements TransactionEventRepository {

    private final SpringDataJpaTransactionEventRepository transactionEventRepository;

    @Override
    public Long save(TransactionEventDomain transactionEventDomain) {
        TransactionEventEntity eventEntity = TransactionEventEntity.from(transactionEventDomain);
        TransactionEventEntity persistedEvent = transactionEventRepository.save(eventEntity);
        return persistedEvent.getId();
    }
}
