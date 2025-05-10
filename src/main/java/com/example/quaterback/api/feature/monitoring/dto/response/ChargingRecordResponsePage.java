package com.example.quaterback.api.feature.monitoring.dto.response;

import com.example.quaterback.api.domain.station.domain.ChargingStationDomain;
import com.example.quaterback.api.feature.monitoring.dto.query.ChargingRecordQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChargingRecordResponsePage {

    private String stationName;
    private String essValue;
    private List<ChargingRecordResponse> records;
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;
    private boolean isLast;

    public static ChargingRecordResponsePage from(Page<ChargingRecordQuery> queryPage, ChargingStationDomain domain) {
        List<ChargingRecordResponse> records = queryPage.getContent()
                .stream()
                .map(ChargingRecordResponse::from)
                .toList();

        return ChargingRecordResponsePage.builder()
                .stationName(domain.getModel()) //todo stationName만들어야됨
                .essValue(String.valueOf(domain.getEssValue())) //todo 퍼센트 계산해야됨
                .records(records)
                .page(queryPage.getNumber())
                .size(queryPage.getSize())
                .totalPages(queryPage.getTotalPages())
                .totalElements(queryPage.getTotalElements())
                .isLast(queryPage.isLast())
                .build();
    }
}