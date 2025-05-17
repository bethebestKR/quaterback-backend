package com.example.quaterback.websocket.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RawOcppMessageRepository extends MongoRepository<RawOcppMessage, String>, RawOcppMessageRepositoryCustom {
}
