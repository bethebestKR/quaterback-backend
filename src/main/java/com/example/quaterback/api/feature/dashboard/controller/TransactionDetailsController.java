package com.example.quaterback.api.feature.dashboard.controller;
import com.example.quaterback.api.domain.txinfo.service.TransactionInfoService;
import com.example.quaterback.api.feature.managing.dto.apiRequest.CreateTransactionInfoRequest;
import com.example.quaterback.api.feature.managing.dto.apiResponse.ApiResponse;
import com.example.quaterback.api.feature.managing.dto.txInfo.PageResponse;
import com.example.quaterback.api.feature.managing.dto.txInfo.TransactionInfoDto;
import com.example.quaterback.api.feature.managing.dto.txInfo.TransactionSummaryDto;
import com.example.quaterback.common.exception.StationIdValidator;
import com.example.quaterback.common.exception.StationNameValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class TransactionDetailsController {
    private final TransactionInfoService transactionInfoService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<TransactionInfoDto>>> getDetails(
            @RequestParam("fistDate") LocalDate firstDate,
            @RequestParam("secondDate") LocalDate secondDate,
            @RequestParam("stationName") String stationName,
            @RequestParam(name = "size") int size,
            @RequestParam(name = "page") int page
    ) {
        LocalDateTime start = firstDate.atStartOfDay();
        LocalDateTime end = secondDate.atTime(LocalTime.MAX);

        // ✅ 여기서 직접 PageRequest 생성
        Pageable pageable = PageRequest.of(page, size);

        Page<TransactionInfoDto> dtoPage = transactionInfoService.getStationTransactionsByStationAndPeriod(
                start, end, stationName, pageable
        );

        PageResponse<TransactionInfoDto> pageResponse = new PageResponse<>(dtoPage);

        return ResponseEntity.ok(new ApiResponse<>("success", pageResponse));
    }

    @GetMapping("api/v1/charging-station/records/summary")
    public ResponseEntity<ApiResponse<TransactionSummaryDto>> getSummaryV1(
            @RequestParam("fistDate") LocalDate firstDate,
            @RequestParam("secondDate") LocalDate secondDate,
            @RequestParam("stationName") String stationName)
    {
        LocalDateTime start = firstDate.atStartOfDay();
        LocalDateTime end = secondDate.atTime(LocalTime.MAX);

        TransactionSummaryDto transactionSummaryDto = transactionInfoService.getChargerTransactionsByStationAndPeriod(
                start, end ,stationName
        );

        return ResponseEntity.ok(new ApiResponse<>("success", transactionSummaryDto));
    }

    @GetMapping("api/v1/charging-station/record")
    public ResponseEntity<ApiResponse<TransactionInfoDto>> getOneTxInfo(
            @RequestParam("transactionId") String transactionId
    ){
        TransactionInfoDto result = transactionInfoService.getOneTxInfo(transactionId);
        return ResponseEntity.ok(new ApiResponse<>("success", result));
    }

     @GetMapping("api/v1/charging-station/names")
     public ResponseEntity<ApiResponse<List<String>>> getStationNames(
     ) {
        return ResponseEntity.ok(new ApiResponse<>("success",
                transactionInfoService.getCsNames()));
     }

     @GetMapping("api/v1/charging-station/chargers")
     public ResponseEntity<ApiResponse<List<Integer>>> getEvseIds(
             @RequestParam("stationName") String stationName
     ){
        StationNameValidator.validateStationName(stationName);
        return ResponseEntity.ok(new ApiResponse<>("success"
                ,transactionInfoService.getEvseIds(stationName)));
     }


    @PostMapping("api/v1/charging-station/create-TxIfo")
    public  ResponseEntity<ApiResponse<String>> createTxInfo(
            @RequestBody CreateTransactionInfoRequest request
    ){
        StationIdValidator.validateStationId(request.getStationId());

        transactionInfoService.saveTxInfo(request);
        return ResponseEntity.ok(new ApiResponse<>("success","created"));
    }
}
