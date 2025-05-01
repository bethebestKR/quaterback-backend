package com.example.quaterback.websocket.meter.value.converter;

import com.example.quaterback.annotation.Converter;
import com.example.quaterback.websocket.MessageUtil;
import com.example.quaterback.websocket.meter.value.domain.MeterValuesDomain;
import com.example.quaterback.websocket.sub.MeterValue;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

@Converter
public class MeterValuesConverter {
    public MeterValuesDomain convertToMeterValuesDomain(JsonNode jsonNode){
        //값들 추출
        String messageId = MessageUtil.getMessageId(jsonNode);
        String messageTypeId = MessageUtil.getMessageTypeId(jsonNode);
        String action  = MessageUtil.getAction(jsonNode);
        JsonNode payload = MessageUtil.getPayload(jsonNode);

        return MeterValuesDomain.builder()
                .messageId(messageId)
                .messageTypeId(messageTypeId)
                .action(action)
                .meterValue(List.of(MeterValue.forMeterValues(payload.path("meterValue").get(0))))
                .build();


    }
}
