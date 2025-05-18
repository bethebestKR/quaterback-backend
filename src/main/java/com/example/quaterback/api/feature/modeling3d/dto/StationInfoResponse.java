package com.example.quaterback.api.feature.modeling3d.dto;

import com.example.quaterback.api.domain.station.domain.ChargingStationDomain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StationInfoResponse {
    private List<StationInfoDto> stations;

    public static StationInfoResponse from(List<ChargingStationDomain> stations) {

        return StationInfoResponse.builder()
                .stations(stations.stream()
                        .map(StationInfoDto::from)
                        .toList())
                .build();
    }
}
