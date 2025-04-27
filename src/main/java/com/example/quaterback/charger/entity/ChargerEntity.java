package com.example.quaterback.charger.entity;

import com.example.quaterback.charger.constant.ChargerStatus;
import com.example.quaterback.charger.domain.ChargerDomain;
import com.example.quaterback.station.entity.ChargingStationEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "charger_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer evseId;

    @Enumerated(EnumType.STRING)
    private ChargerStatus chargerStatus;

    private LocalDateTime updateStatusTimeStamp;

    @ManyToOne
    @JoinColumn(name = "station_id", referencedColumnName = "stationId")
    private ChargingStationEntity station;

    public ChargerDomain toDomain() {
        return ChargerDomain.builder()
                .stationId(station.getStationId())
                .evseId(evseId)
                .chargerStatus(chargerStatus)
                .updateStatusTimeStamp(updateStatusTimeStamp)
                .build();
    }

    public void updateChargerStatus(ChargerStatus status) {
        chargerStatus = status;
        updateStatusTimeStamp = LocalDateTime.now();
    }
}
