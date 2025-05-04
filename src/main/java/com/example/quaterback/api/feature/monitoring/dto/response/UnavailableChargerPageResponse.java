package com.example.quaterback.api.feature.monitoring.dto.response;

import com.example.quaterback.api.domain.txinfo.domain.TransactionInfoDomain;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
public record UnavailableChargerPageResponse(
        List<AvailableChargerUsageDto> usages,
        int currentPage,
        int totalPages,
        long totalElements) {

    public static UnavailableChargerPageResponse from(Page<TransactionInfoDomain> domainPage) {
        List<AvailableChargerUsageDto> usageList = domainPage.getContent().stream()
                .map(AvailableChargerUsageDto::from)
                .toList();

        return UnavailableChargerPageResponse.builder()
                .usages(usageList)
                .currentPage(domainPage.getNumber())
                .totalPages(domainPage.getTotalPages())
                .totalElements(domainPage.getTotalElements())
                .build();
    }
}
