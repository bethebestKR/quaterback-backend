package com.example.quaterback.api.feature.dashboard.dto.response;

import com.example.quaterback.api.domain.price.entity.PricePerMwh;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CsPriceHistory(
        double csPrice,
        LocalDateTime updateTime
) {
    public static CsPriceHistory from(PricePerMwh pricePerMwh) {
        return CsPriceHistory.builder()
                .csPrice(pricePerMwh.getPricePerMwh())
                .updateTime(pricePerMwh.getUpdatedDateTime())
                .build();
    }
}
