package com.example.quaterback.websocket.transaction.event.domain.sub;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MeterValue {
    private LocalDateTime createdMeterTimestamp;
    private LocalDateTime updatedMeterTimestamp;
    private List<SampledValue> sampledValues;
}
