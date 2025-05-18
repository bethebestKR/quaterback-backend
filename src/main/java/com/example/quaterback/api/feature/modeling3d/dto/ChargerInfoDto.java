package com.example.quaterback.api.feature.modeling3d.dto;

import com.example.quaterback.api.domain.charger.constant.ChargerStatus;
import com.example.quaterback.api.domain.charger.domain.ChargerDomain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChargerInfoDto {
    private Integer evseId;
    private String stationId;
    private ChargerStatus status;

    public static ChargerInfoDto from(ChargerDomain domain){
        return ChargerInfoDto.builder()
                .evseId(domain.getEvseId())
                .stationId(domain.getStationId())
                .status(domain.getChargerStatus())
                .build();
    }
}
