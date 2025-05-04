package com.example.quaterback.api.domain.charger.service;

import com.example.quaterback.api.domain.charger.converter.ChargerConverter;
import com.example.quaterback.api.domain.charger.domain.ChargerDomain;
import com.example.quaterback.api.domain.charger.repository.ChargerRepository;
import com.example.quaterback.api.domain.station.domain.ChargingStationDomain;
import com.example.quaterback.api.feature.monitoring.dto.response.EvseIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChargerService {
    private final ChargerRepository chargerRepository;
    private final ChargerConverter converter;

    public List<EvseIdResponse> getEvesId(String stationId){
        List<ChargerDomain> domains = chargerRepository.findAllByStationId(stationId);
        return converter.toEvseIdResponse(domains);
    }
}
