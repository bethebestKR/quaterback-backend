package com.example.quaterback.api.feature.statistics.controller;

import com.example.quaterback.api.feature.statistics.dto.request.ChartType;
import com.example.quaterback.api.feature.statistics.dto.response.StatisticsData;
import com.example.quaterback.api.feature.statistics.dto.response.StatisticsSummary;
import com.example.quaterback.api.feature.statistics.service.StatisticsService;
import com.sun.jdi.CharType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/statistics")
@RestController
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/summary")
    public StatisticsSummary getStatisticsSummary(@RequestParam String timeRange) {
        return statisticsService.getSummary();
    }

    @GetMapping("/cost")
    public StatisticsData getCostStatistics(
            @RequestParam(name = "chartType") ChartType chartType,
            @RequestParam String timeRange
    ) {
        return statisticsService.getCostStatistics(chartType);
    }

    @GetMapping("/charging-volume")
    public StatisticsData getChargingVolumeStatistics(
            @RequestParam(name = "chartType") ChartType chartType,
            @RequestParam String timeRange
    ) {
        return statisticsService.getChargingVolumeStatistics(chartType);
    }

    @GetMapping("/charging-info")
    public StatisticsData getChargingInfoStatistics(
            @RequestParam(name = "chartType") ChartType chartType,
            @RequestParam String timeRange
    ) {
        return statisticsService.getChargingInfoStatistics(chartType);
    }

    @GetMapping("/charger-status")
    public StatisticsData getChargerStatusStatistics(
            @RequestParam(name = "chartType") ChartType chartType,
            @RequestParam String timeRange
    ) {
        return statisticsService.getChargerStatusStatistics(chartType);
    }

    @GetMapping("/power-trading")
    public StatisticsData getPowerTradingStatistics(
            @RequestParam(name = "chartType") ChartType chartType,
            @RequestParam String timeRange
    ) {
        return statisticsService.getPowerTradingStatistics(chartType);
    }
}
