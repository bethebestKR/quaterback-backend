package com.example.quaterback.station.entity;

import com.example.quaterback.station.constant.StationStatus;
import com.example.quaterback.station.domain.ChargingStationDomain;
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

    @Enumerated(EnumType.STRING)
    private StationStatus stationStatus;

    public static ChargingStationEntity from(ChargingStationDomain domain) {
        return ChargingStationEntity.builder()
                .stationId(domain.getStationId())
                .model(domain.getModel())
                .vendorId(domain.getVendorId())
                .latitude(domain.getLatitude())
                .longitude(domain.getLongitude())
                .address(domain.getAddress())
                .updateStatusTimeStamp(domain.getUpdateStatusTimeStamp())
                .stationStatus(domain.getStationStatus())
                .build();
    }

    public ChargingStationDomain toDomain() {
        return ChargingStationDomain.builder()
                .stationId(stationId)
                .model(model)
                .vendorId(vendorId)
                .latitude(latitude)
                .longitude(longitude)
                .address(address)
                .updateStatusTimeStamp(updateStatusTimeStamp)
                .stationStatus(stationStatus)
                .build();
    }

}
