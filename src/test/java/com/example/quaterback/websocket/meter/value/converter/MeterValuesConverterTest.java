package com.example.quaterback.websocket.meter.value.converter;

import com.example.quaterback.websocket.meter.value.domain.MeterValuesDomain;
import com.example.quaterback.websocket.sub.MeterValue;
import com.example.quaterback.websocket.sub.SampledValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MeterValuesConverterTest {

    private MeterValuesConverter converter;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        converter = new MeterValuesConverter();
        objectMapper = new ObjectMapper();
    }

    @Test
    void convertToMeterValuesDomain_정상적으로_변환된다() throws Exception {
        // given
        String jsonString = """
            [
              2,
              "abc123-message-id",
              "MeterValues",
              {
                "meterValue": [
                  {
                    "timestamp": "2025-05-03T17:00:00",
                    "sampledValue": [
                      {
                        "value": 12,
                        "measurand": "Energy.Active.Import.Register"
                      }
                    ]
                  }
                ]
              }
            ]
        """;

        JsonNode jsonNode = objectMapper.readTree(jsonString);

        // when
        MeterValuesDomain result = converter.convertToMeterValuesDomain(jsonNode);

        // then
        assertThat(result.getMessageTypeId()).isEqualTo("2");
        assertThat(result.getMessageId()).isEqualTo("abc123-message-id");
        assertThat(result.getAction()).isEqualTo("MeterValues");

        List<MeterValue> meterValues = result.getMeterValue();
        assertThat(meterValues).hasSize(1);

        MeterValue mv = meterValues.get(0);
        assertThat(mv.getTimestamp()).isEqualTo(LocalDateTime.parse("2025-05-03T17:00:00"));

        List<SampledValue> sampledValues = mv.getSampledValues();
        assertThat(sampledValues).hasSize(1);
        assertThat(sampledValues.get(0).getValue()).isEqualTo(12);
        assertThat(sampledValues.get(0).getMeasurand()).isEqualTo("Energy.Active.Import.Register");
    }
}
