package com.example.quaterback.api.feature.dashboard.dto.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ChargerUsageQuery {
    private LocalDateTime time;
    private String stationAddress;
    private String stationModel;
    private int usageKwh;
    private int priceWon;
    private String confirmCode;

    public String getUsageKwh() {
        return usageKwh+"(kWh)";
    }

    public String getPriceWon() {
        return String.format("%,d(KRW)",priceWon);
    }
}
