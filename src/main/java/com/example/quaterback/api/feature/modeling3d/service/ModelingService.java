package com.example.quaterback.api.feature.modeling3d.service;

import com.example.quaterback.api.domain.charger.constant.ChargerStatus;
import com.example.quaterback.api.domain.charger.domain.ChargerDomain;
import com.example.quaterback.api.domain.charger.repository.ChargerRepository;
import com.example.quaterback.api.domain.charger.repository.SpringDataJpaChargerRepository;
import com.example.quaterback.api.domain.station.constant.StationStatus;
import com.example.quaterback.api.domain.station.domain.ChargingStationDomain;
import com.example.quaterback.api.domain.station.repository.ChargingStationRepository;
import com.example.quaterback.api.domain.station.repository.SpringDataJpaChargingStationRepository;
import com.example.quaterback.api.feature.modeling3d.dto.StationInfoResponse;
import com.example.quaterback.api.feature.modeling3d.dto.UpdateChargerStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModelingService {
    private final ChargingStationRepository chargingStationRepository;
    private final ChargerRepository chargerRepository;

    public StationInfoResponse getStationsInfo() {
        List<ChargingStationDomain> stations = chargingStationRepository.findAll();
        return StationInfoResponse.from(stations);
    }

    public String updateStation(String stationId, StationStatus status) {
        String updatedStationId = chargingStationRepository.updateStationStatus(stationId, status);
        return updatedStationId;
    }

    public UpdateChargerStatusResponse updateCharger(String stationId, Integer evseId, ChargerStatus status) {
        ChargerDomain chargerDomain = chargerRepository.updateChargerStatus(stationId, evseId, status);
        return UpdateChargerStatusResponse.from(chargerDomain);
    }
}
