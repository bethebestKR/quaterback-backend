package com.example.quaterback.station.domain;

import com.example.quaterback.station.constant.StationStatus;
import com.example.quaterback.websocket.boot.notification.domain.BootNotificationDomain;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChargingStationDomain {
    private Long id;
    private String stationId;
    private String model;
    private String vendorId;
    private Double latitude;
    private Double longitude;
    private String address;
    private LocalDateTime updateStatusTimeStamp;
    private StationStatus stationStatus;

    public void updateStationStatus(StationStatus status) {
        this.stationStatus = status;
        this.updateStatusTimeStamp = LocalDateTime.now();
    }
}
