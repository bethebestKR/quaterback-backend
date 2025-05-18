package com.example.quaterback.websocket.mongodb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class MongoDBService {

    private final RawOcppMessageRepository rawOcppMessageRepository;

    public void saveMessage(String jsonString, String stationId, String action) {
        try {
            String wrapped = "{\"data\":" + jsonString + "}";
            Document temp = Document.parse(wrapped);
            Object cleanData = temp.get("data");

            Document wrapper = new Document();
            wrapper.put("message", cleanData);
            wrapper.put("timestamp", LocalDateTime.now());
            wrapper.put("stationId", stationId);
            wrapper.put("action", action);
            RawOcppMessage rawOcppMessage = new RawOcppMessage(wrapper, LocalDateTime.now(), stationId, action);
            rawOcppMessageRepository.save(rawOcppMessage);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
