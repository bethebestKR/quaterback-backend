package com.example.quaterback.websocket.transaction.event.domain.sub;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VehicleInfo {
    private String vehicleNo;
    private String model;
    private Integer batteryCapacityKWh;
    private Integer requestedEnergyKWh;
}
