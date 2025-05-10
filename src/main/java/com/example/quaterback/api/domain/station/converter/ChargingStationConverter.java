package com.example.quaterback.api.domain.station.converter;

import com.example.quaterback.api.domain.station.domain.ChargingStationDomain;
import com.example.quaterback.api.feature.dashboard.dto.query.StationFullInfoQuery;
import com.example.quaterback.api.feature.dashboard.dto.response.StationFullInfoResponse;
import com.example.quaterback.api.feature.monitoring.dto.response.EvseIdResponse;
import com.example.quaterback.api.feature.overview.dto.response.StationOverviewResponse;
import com.example.quaterback.common.annotation.Converter;

import java.util.List;

@Converter
public class ChargingStationConverter {
    public List<StationFullInfoResponse> toStationFullInfos(List<StationFullInfoQuery> queryList) {
        return queryList.stream()
                .map(StationFullInfoResponse::from)
                .toList();
    }

    public StationFullInfoResponse toStationFullInfo(StationFullInfoQuery query) {
        return StationFullInfoResponse.from(query);
    }

    public List<StationOverviewResponse> toStationOverviewResponse(List<ChargingStationDomain> stations) {
        return stations.stream()
                .map(StationOverviewResponse::from)
                .toList();
    }

    public StationOverviewResponse toStationOverviewRespons(ChargingStationDomain station) {
        return StationOverviewResponse.from(station);
    }

}
