package com.example.quaterback.station.entity;

import com.example.quaterback.websocket.boot.notification.domain.BootNotificationDomain;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "charging_station")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargingStationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stationId;
    private String model;
    private String vendorId;
    private Double latitude;
    private Double longitude;
    private String address;

    private LocalDateTime updateStatusTimeStamp;

    private String stationStatus;

    public static ChargingStationEntity from(BootNotificationDomain domain) {
        return ChargingStationEntity.builder()
                .stationId(domain.extractStationId())
                .model(domain.getModel())
                .vendorId(domain.extractVendorId())
                .latitude(domain.extractLatitude())
                .longitude(domain.extractLongitude())
                .address(domain.extractAddress())
                .updateStatusTimeStamp(LocalDateTime.now())
                .stationStatus("active")
                .build();
    }

}
