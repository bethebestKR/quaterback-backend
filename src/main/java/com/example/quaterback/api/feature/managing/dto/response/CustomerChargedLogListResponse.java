package com.example.quaterback.api.feature.managing.dto.response;

import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
public record CustomerChargedLogListResponse(
        List<CustomerChargedLogResponse> customerChargedLogList,
        Integer currentPage,
        Long totalElements,
        Integer totalPages
) {
    public static CustomerChargedLogListResponse from(Page<CustomerChargedLogResponse> page) {
        return CustomerChargedLogListResponse.builder()
                .customerChargedLogList(page.getContent())
                .currentPage(page.getNumber())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}
