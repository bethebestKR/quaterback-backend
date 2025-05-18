package com.example.quaterback.websocket.mongodb;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Document(collection = "ocpp_messages")
public class RawOcppMessage {

    @Id
    private String id;

    private org.bson.Document message;
    private LocalDateTime timestamp;
    private String stationId;
    private String action;

    public RawOcppMessage(org.bson.Document message, LocalDateTime timestamp, String stationId, String action) {
        this.message = message;
        this.timestamp = timestamp;
        this.stationId = stationId;
        this.action = action;
    }
}
