package com.example.quaterback.websocket.sub;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class SampledValue {
    private final Double value;
    private final String measurand;

    public static SampledValue forMeterValues(Double value, String measurand) {
        return new SampledValue(value, measurand);
    }

    public static SampledValue forTransactionEvent(Double value) {
        return new SampledValue(value, null);
    }
}