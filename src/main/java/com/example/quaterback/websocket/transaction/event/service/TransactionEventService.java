package com.example.quaterback.websocket.transaction.event.service;

import com.example.quaterback.websocket.transaction.event.converter.TransactionEventConverter;
import com.example.quaterback.websocket.transaction.event.domain.TransactionEventDomain;
import com.example.quaterback.websocket.transaction.event.repository.TransactionEventRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionEventService {

    private final TransactionEventRepository transactionEventRepository;
    private final TransactionEventConverter converter;

    public Long create(JsonNode jsonNode) {
        TransactionEventDomain domain = converter.convertToStartedDomain(jsonNode);
        Long id = transactionEventRepository.save(domain);
        return id;
    }
}
