package com.example.quaterback.websocket.meter.value.domain;

import com.example.quaterback.websocket.sub.MeterValue;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class MeterValuesDomain {
    private String messageTypeId;
    private String messageId;
    private String action;

    private List<MeterValue> meterValue;

}
