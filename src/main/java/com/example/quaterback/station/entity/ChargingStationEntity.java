package com.example.quaterback.station.entity;

import com.example.quaterback.station.constant.StationStatus;
import com.example.quaterback.station.domain.ChargingStationDomain;
import com.example.quaterback.charger.entity.ChargerEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(unique = true)
    private String stationId;
    private String model;
    private String vendorId;
    private Double latitude;
    private Double longitude;
    private String address;

    private LocalDateTime updateStatusTimeStamp;

    @Enumerated(EnumType.STRING)
    private StationStatus stationStatus;

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL)
    private List<ChargerEntity> ChargerList = new ArrayList<>();
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

    public void updateStationStatus(StationStatus status) {
        stationStatus = status;
        updateStatusTimeStamp = LocalDateTime.now();
    }

}
