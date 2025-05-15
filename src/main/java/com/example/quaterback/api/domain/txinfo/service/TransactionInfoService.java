package com.example.quaterback.api.domain.txinfo.service;

import com.example.quaterback.api.domain.charger.domain.ChargerDomain;
import com.example.quaterback.api.domain.charger.repository.ChargerRepository;
import com.example.quaterback.api.domain.price.service.PriceService;
import com.example.quaterback.api.domain.station.domain.ChargingStationDomain;
import com.example.quaterback.api.domain.station.repository.ChargingStationRepository;
import com.example.quaterback.api.domain.txinfo.domain.TransactionInfoDomain;
import com.example.quaterback.api.domain.txinfo.repository.TxInfoRepository;
import com.example.quaterback.api.domain.txlog.domain.TransactionLogDomain;
import com.example.quaterback.api.domain.txlog.repository.TxLogRepository;
import com.example.quaterback.api.feature.managing.dto.apiRequest.CreateTransactionInfoRequest;
import com.example.quaterback.api.feature.managing.dto.txInfo.TransactionInfoDto;
import com.example.quaterback.api.feature.managing.dto.txInfo.TransactionSummaryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionInfoService {
    private final TxInfoRepository txInfoRepository;
    private final ChargerRepository chargerRepository;
    private final ChargingStationRepository chargingStationRepository;
    private final TxLogRepository txLogRepository;
    private final PriceService priceService;

    //charger 별 충전기록 얻기
    public TransactionSummaryDto getChargerTransactionsByStationAndPeriod(LocalDateTime start,
                                                                          LocalDateTime end,
                                                                          String stationName) {

        TransactionInfoDomain domain = TransactionInfoDomain.fromLocalDateTimeToDomain(
                start
                , end
        );
        //stationId 얻고
        String stationId = chargingStationRepository.findStationIdByStationName(stationName);

        //stationId로 충전소에 관련된 Charger 객체들 뽑고
        List<ChargerDomain> chargerList = new ArrayList<>(chargerRepository.findByStationID(stationId));


        List<TransactionInfoDomain> totalTransactionInfos = new ArrayList<>();

        for (ChargerDomain charger : chargerList) {
            List<TransactionInfoDomain> tmp = txInfoRepository.findByChargerPkAndCreatedAtBetween(domain,
                    charger.getId());
            totalTransactionInfos.addAll(tmp);
        }

        Double allMeterValue = totalTransactionInfos.stream()
                .mapToDouble(TransactionInfoDomain::getTotalMeterValue)
                .sum();
        Double allPrice = totalTransactionInfos.stream()
                .mapToDouble(TransactionInfoDomain::getTotalPrice)
                .sum();

        TransactionSummaryDto transactionSummaryDto = new TransactionSummaryDto(
                allMeterValue, allPrice
        );
        return transactionSummaryDto;
    }


    public Page<TransactionInfoDto> getStationTransactionsByStationAndPeriod(
            LocalDateTime start, LocalDateTime end, String stationName, Pageable pageable
    ) {
        TransactionInfoDomain onlyTimeTxDomain = TransactionInfoDomain.fromLocalDateTimeToDomain(
                start
                , end
        );
        String stationId = chargingStationRepository.findStationIdByStationName(stationName);

        Page<TransactionInfoDomain> txInfoDomains = txInfoRepository.findByStationIdAndCreatedAtBetween(
                onlyTimeTxDomain, stationId, pageable
        );
        Page<TransactionInfoDto> dtoPage = txInfoDomains.map(domain -> new TransactionInfoDto(domain));
        return dtoPage;
    }

    @Transactional
    public TransactionInfoDto getOneTxInfo(String transactionId) {
        TransactionInfoDomain txDomain = TransactionInfoDomain.transactionIdDomain(transactionId);
        TransactionInfoDomain fullTxDomain = txInfoRepository.getOneTxInfoByTxId(txDomain);
        return new TransactionInfoDto(fullTxDomain);
    }


    public void saveTxInfo(CreateTransactionInfoRequest request) {
        TransactionInfoDomain txInfoDomain = request.toDomain();
        txInfoRepository.save(txInfoDomain);
    }

    public List<String> getCsNames() {
        List<ChargingStationDomain> csDomains = chargingStationRepository.findAll();
        List<String> names = csDomains.stream()
                .map(ChargingStationDomain::getStationName)
                .toList();
        return names;
    }

    public List<Integer> getEvseIds(String stationName) {
        String stationId = chargingStationRepository.findStationIdByStationName(stationName);
        List<ChargerDomain> chargerDomains = chargerRepository.findAllByStationId(stationId);
        List<Integer> evseIds = chargerDomains.stream()
                .map(ChargerDomain::getEvseId)
                .toList();
        return evseIds;
    }

    @Transactional
    public String updateTxInfo(String transactionId, String errorCode, LocalDateTime end) {
        if (end == null) {
            TransactionInfoDomain domain = TransactionInfoDomain.fromEndedTxEventDomain(transactionId, LocalDateTime.now(), "01", 0.0, 0.0);
            return txInfoRepository.updateEndTime(domain);
        }
        TransactionInfoDomain oriTxInfoDomain = txInfoRepository.findByTxId(transactionId);
        int avgMeterValue = txLogRepository.getTotalMeterValue(transactionId);
        double pricePerWh = priceService.getCurrentPrice();
        int durationSeconds = (int) Duration.between(oriTxInfoDomain.getStartedTime(), end).getSeconds();
        if (durationSeconds < 0) {
            throw new IllegalArgumentException("종료 시간은 시작 시간보다 이후여야합니다.");
        }
        double totalMeterValue = avgMeterValue / 1000.0 * (durationSeconds / 3600.0);
        totalMeterValue = Math.round(totalMeterValue * 100) / 100.0;
        Double totalPrice = pricePerWh * totalMeterValue;

        TransactionInfoDomain endTxInfoDomain = TransactionInfoDomain.fromEndedTxEventDomain(transactionId, end, errorCode, totalMeterValue, totalPrice);
        String resultTransactionId = txInfoRepository.updateEndTime(endTxInfoDomain);
        return resultTransactionId;
    }

    @Transactional
    public void setErrorCodeToNotEndedTxInfos(String stationId) {
        List<TransactionInfoDomain> notEnded = txInfoRepository.findNotEnded(stationId);
        for (TransactionInfoDomain txInfoDomain : notEnded) {
            LocalDateTime last = txLogRepository.getLastLogDateTime(txInfoDomain.getTransactionId());
            updateTxInfo(txInfoDomain.getTransactionId(), "01", last);
        }
    }

}
