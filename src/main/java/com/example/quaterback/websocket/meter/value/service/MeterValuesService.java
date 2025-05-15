package com.example.quaterback.websocket.meter.value.service;

import com.example.quaterback.api.domain.station.domain.ChargingStationDomain;
import com.example.quaterback.api.domain.station.repository.ChargingStationRepository;
import com.example.quaterback.common.redis.service.RedisMapSessionToStationService;
import com.example.quaterback.websocket.meter.value.converter.MeterValuesConverter;
import com.example.quaterback.websocket.meter.value.domain.MeterValuesDomain;
import com.example.quaterback.websocket.sub.MeterValue;
import com.example.quaterback.websocket.sub.SampledValue;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeterValuesService {
    private final MeterValuesConverter meterValuesConverter;
    private final RedisMapSessionToStationService redisService;
    private final ChargingStationRepository chargingStationRepository;

    @Transactional
    public String updateStationEss(JsonNode jsonNode, String sessionId) {
        MeterValuesDomain meterValuesDomain = meterValuesConverter.convertToMeterValuesDomain(jsonNode);
        MeterValue meterValue = meterValuesDomain.extractFirstMeterValue();
        SampledValue sampledValue = meterValue.extractFirstSampledValue();
        Double value = sampledValue.getValue();

        String stationId = redisService.getStationId(sessionId);

        ChargingStationDomain chargingStationDomain = chargingStationRepository.findByStationId(stationId);

        chargingStationDomain.updateStationEssValue(value);

        stationId = chargingStationRepository.updateEss(chargingStationDomain);
        return stationId;
    }

}
