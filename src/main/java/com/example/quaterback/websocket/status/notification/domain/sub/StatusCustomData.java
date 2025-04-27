package com.example.quaterback.websocket.status.notification.domain.sub;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StatusCustomData {
    private String vendorId;
    private String stationId;
}
