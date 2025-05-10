package com.example.quaterback.api.domain.charger.domain;

import com.example.quaterback.api.domain.charger.constant.ChargerStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChargerDomain {
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
}
