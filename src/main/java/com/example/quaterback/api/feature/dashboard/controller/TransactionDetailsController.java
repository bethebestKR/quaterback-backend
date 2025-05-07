package com.example.quaterback.api.feature.dashboard.controller;

import com.example.quaterback.api.domain.station.service.ChargingStationService;
import com.example.quaterback.api.domain.txinfo.entity.TransactionInfoEntity;
import com.example.quaterback.api.domain.txinfo.service.TransactionInfoService;
import com.example.quaterback.api.feature.managing.dto.apiRequest.CreateTransactionInfoRequest;
import com.example.quaterback.api.feature.managing.dto.apiResponse.ApiResponse;
import com.example.quaterback.api.feature.managing.dto.txInfo.PageResponse;
import com.example.quaterback.api.feature.managing.dto.txInfo.TransactionInfoDto;
import com.example.quaterback.api.feature.managing.dto.txInfo.TransactionSummaryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TransactionDetailsController {
    private final TransactionInfoService transactionInfoService;
    private final ChargingStationService chargingStationService;

    @GetMapping(value = "api/v1/charging-station/records", produces = "application/json")
    public ResponseEntity<ApiResponse<PageResponse<TransactionInfoDto>>> getDetailsV1(
            @RequestParam("fistDate") LocalDate firstDate,
            @RequestParam("secondDate") LocalDate secondDate,
            @RequestParam("stationName") String stationName,
            Pageable pageable
    ){
        LocalDateTime start = firstDate.atStartOfDay();
        LocalDateTime end = secondDate.atTime(LocalTime.MAX);

        Page<TransactionInfoEntity> transactionInfoPage = transactionInfoService.getStationTransactionsByStationAndPeriod(
                start, end, stationName, pageable
        );

       Page<TransactionInfoDto> dtoPage = transactionInfoPage.map(TransactionInfoDto :: new);
       PageResponse<TransactionInfoDto> pageResponse = new PageResponse<>(dtoPage);

        return ResponseEntity.ok(new ApiResponse("success", pageResponse));
    }

    @GetMapping("api/v1/charging-station/records/summary")
    public ResponseEntity<ApiResponse<TransactionSummaryDto>> getSummaryV1(
            @RequestParam("fistDate") LocalDate firstDate,
            @RequestParam("secondDate") LocalDate secondDate,
            @RequestParam("stationName") String stationName)
    {
        LocalDateTime start = firstDate.atStartOfDay();
        LocalDateTime end = secondDate.atTime(LocalTime.MAX);
        List<TransactionInfoEntity> txInfoEntities = transactionInfoService.getChargerTransactionsByStationAndPeriod(
                start,end,stationName
        );

        Integer allMeterValue = txInfoEntities.stream()
                .mapToInt(TransactionInfoEntity :: getTotalMeterValue)
                .sum();
        Integer allPrice = txInfoEntities.stream()
                .mapToInt(TransactionInfoEntity :: getTotalPrice)
                .sum();
        TransactionSummaryDto transactionSummaryDto = new TransactionSummaryDto(
                allMeterValue, allPrice
        );
        return ResponseEntity.ok(new ApiResponse<>("success", transactionSummaryDto));
    }


//     @GetMapping("api/v1/charging-station/names")
//     public List<String> getStationNames(
//     ) {
//        chargingStationService.getStationNames();
//     }
//    @GetMapping(value = "api/v1/charging-station/record", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<TransactionInfoDto> getTxInfo(
//            @RequestParam("transactionId") String transactionId
//    ){
//        TransactionInfoEntity txInfoEntity = transactionInfoService.getTxInfoByTxId(transactionId);
//        TransactionInfoDto txInfoDto = new TransactionInfoDto(txInfoEntity);
//        return ResponseEntity.ok(txInfoDto);
//    }



    @PostMapping("api/v1/charging-station/create-TxIfo")
    public  ResponseEntity<ApiResponse<String>> createTxInfo(
            @RequestBody CreateTransactionInfoRequest request
    ){
        log.info("adadf{}", request.getTotalMeterValue());
        transactionInfoService.saveTxInfo(request);

        return ResponseEntity.ok(new ApiResponse<>("success","created"));
    }
}
