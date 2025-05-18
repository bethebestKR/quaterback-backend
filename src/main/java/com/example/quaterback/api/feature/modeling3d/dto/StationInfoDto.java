package com.example.quaterback.api.feature.modeling3d.dto;

import com.example.quaterback.api.domain.station.constant.StationStatus;
import com.example.quaterback.api.domain.station.domain.ChargingStationDomain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StationInfoDto {
    private String stationName;
    private Double latitude;
    private Double longitude;
    private String address;
    private String stationId;
    private StationStatus status;
    List<ChargerInfoDto> chargers;

    public static StationInfoDto from(ChargingStationDomain domain){
        return StationInfoDto.builder()
                .stationName(domain.getStationName())
                .latitude(domain.getLatitude())
                .longitude(domain.getLongitude())
                .address(domain.getAddress())
                .stationId(domain.getStationId())
                .status(domain.getStationStatus())
                .chargers(domain.getChargers().stream()
                        .map(ChargerInfoDto::from)
                        .toList())
                .build();
    }

}
