package com.example.quaterback.api.feature.overview.service;

import com.example.quaterback.api.domain.charger.domain.ChargerDomain;
import com.example.quaterback.api.domain.charger.repository.ChargerRepository;
import com.example.quaterback.api.domain.charger.service.ChargerService;
import com.example.quaterback.api.domain.station.domain.ChargingStationDomain;
import com.example.quaterback.api.domain.station.repository.ChargingStationRepository;
import com.example.quaterback.api.domain.station.service.StationService;
import com.example.quaterback.api.feature.overview.dto.response.CsAndChargerParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OverviewService {
    private final ChargingStationRepository chargingStationRepository;
    private final ChargerRepository chargerRepository;

    @Transactional
    public void createCsAndCharger(CsAndChargerParams request){
        ChargingStationDomain csDomain = ChargingStationDomain.fromRequestToCsDomain(request);
        chargingStationRepository.save(csDomain);

        String stationId = request.getStationId();
        for(Integer i =1; i< request.getEvseCnt(); i++){
            ChargerDomain chargerDomain = ChargerDomain.fromRequestToDomain(i, stationId);
            chargerRepository.save(chargerDomain);
        }
    }
}
