package com.example.quaterback.api.feature.managing.dto.response;

import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
public record CustomerListResponse(
        List<CustomerResponse> customerList,
        Integer currentPage,
        Integer totalPages,
        Long totalElements
) {
    public static CustomerListResponse from(Page<CustomerResponse> page) {
        return CustomerListResponse.builder()
                .customerList(page.getContent())
                .currentPage(page.getNumber())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();
    }
}
