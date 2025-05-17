package com.example.quaterback.api.feature.ocpplog.controller;

import com.example.quaterback.api.feature.ocpplog.dto.request.OcppLogFilterRequest;
import com.example.quaterback.api.feature.ocpplog.dto.response.OcppLogSearchResult;
import com.example.quaterback.api.feature.ocpplog.dto.response.StationActionList;
import com.example.quaterback.api.feature.ocpplog.service.OcppLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ocpp-log")
public class OcppLogController {

    private final OcppLogService ocppLogService;

    @PostMapping("/search")
    public OcppLogSearchResult search(@RequestBody OcppLogFilterRequest filter) {
        return ocppLogService.search(filter);
    }

    @GetMapping("/station-action-list")
    public StationActionList getStationActionList() {
        return ocppLogService.getStationActionList();
    }

}
