package com.example.quaterback.websocket.boot.notification.domain;

import com.example.quaterback.websocket.boot.notification.domain.sub.CustomData;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BootNotificationDomain {
    private String messageTypeId;
    private String messageId;
    private String action;

    private String reason;
    private String model;

    private CustomData customData;

    public String extractStationId() {
        return customData.getStationId();
    }

    public String extractVendorId() {
        return customData.getVendorId();
    }

    public Double extractLatitude() {
        return customData.getLocation().getLatitude();
    }

    public Double extractLongitude() {
        return customData.getLocation().getLongitude();
    }

    public String extractAddress() {
        return customData.getLocation().getAddress();
    }

}
