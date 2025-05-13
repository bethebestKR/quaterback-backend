package com.example.quaterback.api.feature.monitoring.dto.response;

import com.example.quaterback.api.domain.txinfo.domain.TransactionInfoDomain;
import com.example.quaterback.api.feature.monitoring.dto.query.DailyUsageQuery;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
public record AvailableChargerPageResponse(
        //일일정보
        double totalChargedEnergy,
        int totalVehicleCount,
        double totalRevenue,

        //퍼센트계산
        int chargedEnergyDiffPercent,
        int vehicleCountDiffPercent,
        int revenueDiffPercent,

        //페이지네이션
        List<AvailableChargerUsageDto> usages,
        int currentPage,
        int totalPages,
        long totalElements


) {
    public static AvailableChargerPageResponse from(DailyUsageDto dailyUsage, Page<TransactionInfoDomain> domainPage) {
        List<AvailableChargerUsageDto> usageList = domainPage.getContent().stream()
                .map(AvailableChargerUsageDto::from)
                .toList();

        return AvailableChargerPageResponse.builder()
                .totalChargedEnergy(dailyUsage.getTotalChargedEnergy())
                .totalVehicleCount(dailyUsage.getTotalVehicleCount().intValue())
                .totalRevenue(dailyUsage.getTotalRevenue())
                .chargedEnergyDiffPercent(dailyUsage.getChargedEnergyDiffPercent())
                .vehicleCountDiffPercent(dailyUsage.getVehicleCountDiffPercent())
                .revenueDiffPercent(dailyUsage.getRevenueDiffPercent())
                .usages(usageList)
                .currentPage(domainPage.getNumber())
                .totalPages(domainPage.getTotalPages())
                .totalElements(domainPage.getTotalElements())
                .build();
    }


}
