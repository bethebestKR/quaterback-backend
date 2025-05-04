package com.example.quaterback.api.feature.monitoring.dto.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HourlyCongestionQuery {
    private int hour;
    private Long count;
}
