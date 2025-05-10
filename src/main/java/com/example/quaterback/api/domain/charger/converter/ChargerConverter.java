package com.example.quaterback.api.domain.charger.converter;

import com.example.quaterback.api.domain.charger.domain.ChargerDomain;
import com.example.quaterback.api.feature.monitoring.dto.response.EvseIdResponse;
import com.example.quaterback.common.annotation.Converter;

import java.util.List;

@Converter
public class ChargerConverter {
    public List<EvseIdResponse> toEvseIdResponse(List<ChargerDomain> domains) {
        return domains.stream()
                .map(EvseIdResponse::from)
                .toList();
    }
}
