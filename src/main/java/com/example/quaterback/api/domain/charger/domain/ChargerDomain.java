package com.example.quaterback.api.domain.charger.domain;

import com.example.quaterback.api.domain.charger.constant.ChargerStatus;
import com.example.quaterback.api.domain.charger.entity.ChargerEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChargerDomain {
    private Long id;
    private Integer evseId;
    private ChargerStatus chargerStatus;
    private LocalDateTime updateStatusTimeStamp;
    private String stationId;

    public void updateChargerStatus(ChargerStatus status) {
        if (!chargerStatus.equals(status)) {
            this.chargerStatus = status;
            this.updateStatusTimeStamp = LocalDateTime.now();
        }
    }

    public static ChargerDomain fromEntityToDomain(ChargerEntity entity){
        return ChargerDomain.builder()
                .id(entity.getId())
                .evseId(entity.getEvseId())
                .chargerStatus(entity.getChargerStatus())
                .updateStatusTimeStamp(entity.getUpdateStatusTimeStamp())
                .stationId(entity.getStation().getStationId())
                .build();
    }
}
