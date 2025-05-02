package com.example.quaterback.websocket.transaction.event.domain.sub;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionCustomData {
    private String vendorId;
    private VehicleInfo vehicleInfo;
}
