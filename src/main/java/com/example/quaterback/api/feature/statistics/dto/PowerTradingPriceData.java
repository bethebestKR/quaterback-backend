package com.example.quaterback.api.feature.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PowerTradingPriceData {
    private double averagePrice;
    private List<TimeSlotPrice> priceByTimeSlot;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TimeSlotPrice {
        private String timeSlot; // 예: "심야 (23-06시)"
        private double price;    // 예: 85
    }
}
