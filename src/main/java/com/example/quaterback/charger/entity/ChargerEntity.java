package com.example.quaterback.charger.entity;

import com.example.quaterback.station.entity.ChargingStationEntity;
import com.example.quaterback.websocket.status.notification.domain.StatusNotificationDomain;
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
    private String chargerStatus;

    private LocalDateTime updateStatusTimeStamp;

    @ManyToOne
    @JoinColumn(name = "station_id", referencedColumnName = "stationId")
    private ChargingStationEntity station;
}
