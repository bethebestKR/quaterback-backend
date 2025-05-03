package com.example.quaterback.api.domain.station.domain;

import com.example.quaterback.api.domain.station.constant.StationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChargingStationDomain {
    private String stationId;
    private String model;
    private String vendorId;
    private Double latitude;
    private Double longitude;
    private String address;
    private LocalDateTime updateStatusTimeStamp;
    private StationStatus stationStatus;

    public void updateStationStatus(StationStatus status) {
        if (!this.stationStatus.equals(status)) {
            this.stationStatus = status;
            this.updateStatusTimeStamp = LocalDateTime.now();
        }
    }
}
