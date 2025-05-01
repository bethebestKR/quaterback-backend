package com.example.quaterback.websocket.meter.value.service;

import com.example.quaterback.redis.service.RedisMapSessionToStationService;
import com.example.quaterback.station.domain.ChargingStationDomain;
import com.example.quaterback.station.repository.JpaChargingStationRepository;
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
    private final JpaChargingStationRepository jpaChargingStationRepository;

    @Transactional
    public String updateStationEss(JsonNode jsonNode, String sessionId) {
        MeterValuesDomain meterValuesDomain = meterValuesConverter.convertToMeterValuesDomain(jsonNode);
        MeterValue meterValue = meterValuesDomain.getMeterValue().get(0);
        SampledValue sampledValue = meterValue.getSampledValues().get(0);
        Integer value = sampledValue.getValue();


        //Redis를 이용해 인메모리에 있는 sessionId 의 value 값으로 되어있는 stationId 추출
        String stationId = redisService.getStationId(sessionId);

        //해당 아이디를 이용해 해당하는 충전소 Entity 객체 하나를 뽑아오면서 동시에 domain 으로 변환한 걸 반환
        ChargingStationDomain chargingStationDomain = jpaChargingStationRepository.findByStationId(stationId);

        //뽑아온 도메인을 수정하고
        chargingStationDomain.updateStationEssValue(value);

        //수정된 도메인을 통해 Entity를 수정한다.
        stationId = jpaChargingStationRepository.updateEss(chargingStationDomain);
        return stationId;
    }

}
