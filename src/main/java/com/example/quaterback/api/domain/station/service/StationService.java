package com.example.quaterback.api.domain.station.service;

import com.example.quaterback.api.domain.station.converter.ChargingStationConverter;
import com.example.quaterback.api.domain.station.domain.ChargingStationDomain;
import com.example.quaterback.api.domain.station.repository.ChargingStationRepository;
import com.example.quaterback.api.feature.dashboard.dto.query.StationFullInfoQuery;
import com.example.quaterback.api.feature.dashboard.dto.response.DeleteResultResponse;
import com.example.quaterback.api.feature.dashboard.dto.response.StationFullInfoResponse;
import com.example.quaterback.api.feature.monitoring.dto.response.EvseIdResponse;
import com.example.quaterback.api.feature.overview.dto.response.StationOverviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StationService {
    private final ChargingStationRepository chargingStationRepository;
    private final ChargingStationConverter converter;


    public List<StationFullInfoResponse> getStationInfos() {
        List<StationFullInfoQuery> queryList = chargingStationRepository.getFullStationInfos();
        return converter.toStationFullInfos(queryList);
    }

    public StationFullInfoResponse getStationInfo(String stationName) {
        StationFullInfoQuery query = chargingStationRepository.getFullStationInfo(stationName);
        return converter.toStationFullInfo(query);
    }

    public ChargingStationDomain getStation(String stationId){
        return chargingStationRepository.findByStationId(stationId);
    }

    @Transactional
    public DeleteResultResponse removeStation(String stationName) {
        chargingStationRepository.deleteByName(stationName);
        return new DeleteResultResponse(stationName, "삭제완료");
    }

    public List<StationOverviewResponse> getStationOverviews() {
        List<ChargingStationDomain> stations = chargingStationRepository.findAll();
        return converter.toStationOverviewResponse(stations);
    }

    public StationOverviewResponse getStationOverview(String stationName) {
        ChargingStationDomain station = chargingStationRepository.findByStationName(stationName);
        return converter.toStationOverviewRespons(station);
    }
}
