package com.example.quaterback.api.feature.overview.dto.response;

import com.example.quaterback.api.domain.charger.domain.ChargerDomain;
import com.example.quaterback.api.domain.station.constant.StationStatus;
import com.example.quaterback.api.domain.station.domain.ChargingStationDomain;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CsAndChargerParams {
    @NotBlank
    private String stationId;
    private String stationName;
    private String model;
    private String vendorId;
    private Double latitude;
    private Double longitude;
    private String address;
    private Integer evseCnt;
}
