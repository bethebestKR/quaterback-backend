package com.example.quaterback.api.feature.statistics.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsData {
    private List<ChartData> barChartData;
    private List<ChartData> lineChartData;
    private List<ChartData> pieChartData;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChartData {
        private String id;     // pie chart 용
        private String label;
        private int value;
    }
}
