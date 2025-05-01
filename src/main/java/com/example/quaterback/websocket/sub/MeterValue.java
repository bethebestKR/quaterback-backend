package com.example.quaterback.websocket.sub;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class MeterValue {
    private LocalDateTime createdMeterTimestamp;
    private LocalDateTime updatedMeterTimestamp;
    private List<SampledValue> sampledValues;

    // MeterValues 메시지용 팩토리
    public static MeterValue forMeterValues(JsonNode node) {
        return MeterValue.builder()
                .createdMeterTimestamp(LocalDateTime.parse(node.path("timestamp").asText()))
                .sampledValues(List.of(
                        SampledValue.forMeterValues(
                                node.path("sampledValue").get(0).path("value").asInt(),
                                node.path("sampledValue").get(0).path("measurand").asText()
                        )
                ))
                .build();
    }

    public static MeterValue forTransaction(JsonNode node){
        return MeterValue.builder()
                .createdMeterTimestamp(LocalDateTime.parse(node.path("timestamp").asText()))
                .sampledValues(List.of(
                        SampledValue.forTransactionEvent(
                                node.path("sampledValue").get(0).path("value").asInt()
                                )
                ))
                .build();
    }
}
