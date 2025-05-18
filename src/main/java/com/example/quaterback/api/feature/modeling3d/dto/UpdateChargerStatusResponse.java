package com.example.quaterback.api.feature.modeling3d.dto;

import com.example.quaterback.api.domain.charger.domain.ChargerDomain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateChargerStatusResponse {
    private String stationId;
    private Integer evseId;

    public static UpdateChargerStatusResponse from(ChargerDomain domain){
        return UpdateChargerStatusResponse.builder()
                .stationId(domain.getStationId())
                .evseId(domain.getEvseId())
                .build();
    }
}
