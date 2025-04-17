package com.example.quaterback.websocket.transaction.event.domain.sub;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SampledValue {
    private Integer value;
}
