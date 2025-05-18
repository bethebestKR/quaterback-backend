package com.example.quaterback.api.feature.statistics.controller;

import com.example.quaterback.api.feature.statistics.dto.response.StatisticsData;
import com.example.quaterback.api.feature.statistics.dto.response.StatisticsSummary;
import com.example.quaterback.api.feature.statistics.service.StatisticsService;
import com.example.quaterback.api.domain.price.constant.Season;
import com.example.quaterback.api.domain.price.service.KepcoService;
import com.example.quaterback.api.feature.managing.dto.apiResponse.ApiResponse;
import com.example.quaterback.api.feature.statistics.dto.*;
import com.example.quaterback.api.feature.statistics.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequestMapping("/api/statistics")
@RestController
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticService statisticService;
    private final KepcoService kepcoService;

    @GetMapping("/charger-uptime")
    public ResponseEntity<ApiResponse<ChargerUptimeData>> getChargerRate(
    ){
        LocalDateTime startTime = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime endTime = LocalDateTime.now();

        ChargerUptimeData c = statisticService.getChargerRate(startTime, endTime);

        return ResponseEntity.ok(new ApiResponse<>("success", c));
    }

    @GetMapping("/charger-troubles")
    public ResponseEntity<ApiResponse<ChargerTroubleData>> getChargerTrouble(){
        ChargerTroubleData c = statisticService.getChargerTrouble();
        return ResponseEntity.ok(new ApiResponse<>("success", c));
    }
    @PostMapping("/charger-trouble-report")
    public ResponseEntity<ApiResponse<String>> reportChargerTrouble(
            @RequestBody TroubleRequest troubleRequest
    ){
        statisticService.reportTrouble(troubleRequest.getStationName()
                ,troubleRequest.getEvseId());
        return ResponseEntity.ok(new ApiResponse<>("success", "reported"));
    }


    @GetMapping("/power-trading-revenue")
    public ResponseEntity<ApiResponse<PowerTradingRevenueData>> getPowerTradingRevenueData(){
        LocalDateTime startTime = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime endTime = LocalDateTime.now();

        PowerTradingRevenueData c = statisticService.getPowerTradingData(startTime, endTime);
        return ResponseEntity.ok(new ApiResponse<>("success", c));
    }

    @GetMapping("/power-trading-volume")
    public ResponseEntity<ApiResponse<PowerTradingVolumeData>> getPowerTradingVolumeData(){
        LocalDateTime startTime = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime endTime = LocalDateTime.now();

        PowerTradingVolumeData c = statisticService.getPowerVolumeData(startTime, endTime);
        return ResponseEntity.ok(new ApiResponse<>("success", c));
    }

    @GetMapping("/power-trading-price")
    public ResponseEntity<ApiResponse<PowerTradingPriceData>> getPowerTradingPrice(){
       Season season =  kepcoService.determineSeason(LocalDateTime.now());
       PowerTradingPriceData c = statisticService.getTradingPriceBySeason(season);
       return ResponseEntity.ok(new ApiResponse<>("success", c));
    }
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
