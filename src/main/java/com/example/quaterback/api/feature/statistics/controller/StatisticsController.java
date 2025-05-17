package com.example.quaterback.api.feature.statistics.controller;

import com.example.quaterback.api.feature.statistics.dto.response.StatisticsData;
import com.example.quaterback.api.feature.statistics.dto.response.StatisticsSummary;
import com.example.quaterback.api.feature.statistics.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/statistics")
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
            @RequestParam String chartType,
            @RequestParam String timeRange
    ) {
        return statisticsService.getCostStatistics();
    }

    @GetMapping("/charging-volume")
    public StatisticsData getChargingVolumeStatistics(
            @RequestParam String chartType,
            @RequestParam String timeRange
    ) {
        return statisticsService.getChargingVolumeStatistics();
    }

    @GetMapping("/charging-info")
    public StatisticsData getChargingInfoStatistics(
            @RequestParam String chartType,
            @RequestParam String timeRange
    ) {
        return statisticsService.getChargingInfoStatistics();
    }

    @GetMapping("/charger-status")
    public StatisticsData getChargerStatusStatistics(
            @RequestParam String chartType,
            @RequestParam String timeRange
    ) {
        return statisticsService.getChargerStatusStatistics();
    }

    @GetMapping("/power-trading")
    public StatisticsData getPowerTradingStatistics(
            @RequestParam String chartType,
            @RequestParam String timeRange
    ) {
        return statisticsService.getPowerTradingStatistics();
    }
}
