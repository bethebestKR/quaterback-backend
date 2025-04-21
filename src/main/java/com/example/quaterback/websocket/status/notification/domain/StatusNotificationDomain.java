package com.example.quaterback.websocket.status.notification.domain;

import com.example.quaterback.websocket.status.notification.domain.sub.CustomData;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StatusNotificationDomain {
    private String messageTypeId;
    private String messageId;
    private String action;

    private LocalDateTime timeStamp;
    private String connectorStatus;
    private Integer evseId;
    private Integer connectorId;

    private CustomData customData;

    public String extractVendorId() {
        return customData.getVendorId();
    }

    public String extractStationId() {
        return customData.getStationId();
    }
}
