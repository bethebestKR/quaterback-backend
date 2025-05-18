package com.example.quaterback.api.feature.statistics.converter;

import com.example.quaterback.api.feature.statistics.dto.request.ChartType;
import com.example.quaterback.api.feature.statistics.dto.response.StatisticsData;
import com.example.quaterback.common.annotation.Converter;

import java.util.Collections;
import java.util.List;

@Converter
public class StatisticsConverter {

    public StatisticsData toStatisticsData(List<StatisticsData.ChartData> results, ChartType chartType) {
        switch (chartType) {
            case LINE:
                return StatisticsData.builder()
                        .lineChartData(results)
                        .barChartData(Collections.emptyList())
                        .pieChartData(Collections.emptyList())
                        .build();
            case BAR:
                return StatisticsData.builder()
                        .barChartData(results)
                        .lineChartData(Collections.emptyList())
                        .pieChartData(Collections.emptyList())
                        .build();
            case PIE:
                return StatisticsData.builder()
                        .pieChartData(results)
                        .barChartData(Collections.emptyList())
                        .lineChartData(Collections.emptyList())
                        .build();
            default:
                throw new IllegalArgumentException("Unsupported chart type: " + chartType);
        }
    }
}
