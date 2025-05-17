package com.example.quaterback.api.feature.ocpplog.service;

import com.example.quaterback.api.domain.station.domain.ChargingStationDomain;
import com.example.quaterback.api.domain.station.repository.ChargingStationRepository;
import com.example.quaterback.api.feature.ocpplog.constant.OcppAction;
import com.example.quaterback.api.feature.ocpplog.dto.request.OcppLogFilterRequest;
import com.example.quaterback.api.feature.ocpplog.dto.response.OcppLogSearchResult;
import com.example.quaterback.api.feature.ocpplog.dto.response.StationActionList;
import com.example.quaterback.websocket.mongodb.RawOcppMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OcppLogService {

    private final RawOcppMessageRepository repository;
    private final ChargingStationRepository chargingStationRepository;

    public OcppLogSearchResult search(OcppLogFilterRequest filter) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), Sort.by(Sort.Direction.DESC, "timestamp"));
        return repository.findByFilters(filter, pageable);
    }

    public StationActionList getStationActionList() {
        List<String> actionList = Arrays.stream(OcppAction.values())
                .map(Enum::name)
                .toList();
        List<String> stationIdList = chargingStationRepository.findAll()
                .stream().map(ChargingStationDomain::getStationId).toList();
        return new StationActionList(
                stationIdList, actionList
        );
    }
}
