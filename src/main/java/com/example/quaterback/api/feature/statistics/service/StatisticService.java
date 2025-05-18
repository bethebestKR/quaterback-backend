package com.example.quaterback.api.feature.statistics.service;

import com.example.quaterback.api.domain.charger.constant.ChargerStatus;
import com.example.quaterback.api.domain.charger.domain.ChargerDomain;
import com.example.quaterback.api.domain.charger.entity.ChargerEntity;
import com.example.quaterback.api.domain.charger.repository.ChargerRepository;
import com.example.quaterback.api.domain.chargerUptime.entity.ChargerUptimeEntity;
import com.example.quaterback.api.domain.chargerUptime.repository.ChargerInfoRepository;
import com.example.quaterback.api.domain.price.constant.Season;
import com.example.quaterback.api.domain.price.entity.KepcoPrice;
import com.example.quaterback.api.domain.price.repository.KepcoRepository;
import com.example.quaterback.api.domain.station.repository.ChargingStationRepository;
import com.example.quaterback.api.domain.txinfo.entity.TransactionInfoEntity;
import com.example.quaterback.api.domain.txinfo.repository.TxInfoRepository;
import com.example.quaterback.api.feature.statistics.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticService {

    private final ChargerInfoRepository chargerInfoRepository;
    private final ChargerRepository chargerRepository;
    private final TxInfoRepository txInfoRepository;
    private final KepcoRepository kepcoRepository;
    private final ChargingStationRepository chargingStationRepository;


    public ChargerUptimeData getChargerRate(LocalDateTime startTime, LocalDateTime endTime) {
        LocalDate today = LocalDate.now();
        LocalDate prevMonthStart = today.minusMonths(1).withDayOfMonth(1);
        LocalDate prevMonthEnd = today.minusMonths(1);

        LocalDateTime prevStartTime = prevMonthStart.atStartOfDay();
        LocalDateTime prevEndTime = prevMonthEnd.atTime(endTime.toLocalTime());

        List<ChargerUptimeEntity> prevChargerInfoEntities = chargerInfoRepository.getInfosByTimeRange(
                prevStartTime, prevEndTime);

        List<ChargerUptimeEntity> chargerInfoEntities = chargerInfoRepository.getInfosByTimeRange(
                startTime, endTime);

        // 현재 기간 평균
        double overallUptime = chargerInfoEntities.stream()
                .mapToDouble(ChargerUptimeEntity::getUpTime)
                .average()
                .orElse(0.0);

        // 이전 기간 평균
        double preOverallUptime = prevChargerInfoEntities.stream()
                .mapToDouble(ChargerUptimeEntity::getUpTime)
                .average()
                .orElse(0.0);

        // 변화율 계산
        double changePercent = 0.0;
        if (preOverallUptime > 0) {
            changePercent = ((overallUptime - preOverallUptime) / preOverallUptime) * 100;
        }

        // 충전소별 가동률 리스트 구성
        List<ChargerUptimeData.StationUptime> stationUptimeList = chargerInfoEntities.stream()
                .map(entity -> new ChargerUptimeData.StationUptime(
                        entity.getStation().getStationName(),
                        entity.getUpTime()
                ))
                .collect(Collectors.toList());

        return new ChargerUptimeData(
                overallUptime,
                changePercent,
                stationUptimeList
        );
    }

    public ChargerTroubleData getChargerTrouble(){
        List<ChargerEntity> chargerEntities = chargerRepository.findAllCharger();

        List<ChargerTroubleData.ChargerFailure> failureList = chargerEntities.stream()
                .map(entity -> new ChargerTroubleData.ChargerFailure(
                        entity.getStation().getStationName(),                                 // id
                        "충전기 #" + entity.getEvseId(),                                 // name
                        entity.getTroubleCnt() != null ? entity.getTroubleCnt() : 0     // failureCount
                ))
                .toList();

        ChargerTroubleData responseDto = new ChargerTroubleData(failureList);
        return responseDto;
    }

    public PowerTradingRevenueData getPowerTradingData(LocalDateTime startTime, LocalDateTime endTime){
        List<TransactionInfoEntity> txList = txInfoRepository.findTxInfoByTerm(startTime, endTime);
        double netRevenue = txList.stream()
                .mapToDouble(tx -> tx.getTotalPrice() != null ? tx.getTotalPrice() : 0.0)
                .sum();
        return new PowerTradingRevenueData(netRevenue);
    }

    public PowerTradingVolumeData getPowerVolumeData(LocalDateTime startTime, LocalDateTime endTime){
        List<TransactionInfoEntity> txList = txInfoRepository.findTxInfoByTerm(startTime, endTime);
        List<Double> meterValues = txList.stream()
                .map(TransactionInfoEntity::getTotalMeterValue)
                .filter(Objects::nonNull)
                .toList();
        double netVolume = meterValues.stream().mapToDouble(Double::doubleValue).sum();
        double minVolume = meterValues.stream().mapToDouble(Double::doubleValue).min().orElse(0.0);
        double maxVolume = meterValues.stream().mapToDouble(Double::doubleValue).max().orElse(0.0);

        return new PowerTradingVolumeData(netVolume, minVolume, maxVolume);
    }

    public PowerTradingPriceData getTradingPriceBySeason(Season season){
        List<KepcoPrice> kepcoPrices = kepcoRepository.findBySeason(season);

        List<PowerTradingPriceData.TimeSlotPrice> priceByTimeSlot = kepcoPrices.stream()
                .map(p-> new PowerTradingPriceData.TimeSlotPrice(
                        p.getTimeSlot().name(),
                        p.getPricePerKwh()
                ))
                .toList();
        double averagePrice = kepcoPrices.stream()
                .mapToDouble(KepcoPrice :: getPricePerKwh)
                .average()
                .orElse(0.0);

        return new PowerTradingPriceData(averagePrice, priceByTimeSlot);
    }

    @Transactional
    public void reportTrouble(String stationName, Integer evseId){
        String stationId = chargingStationRepository.findStationIdByStationName(stationName);
        ChargerDomain chargerDomain = chargerRepository.findByStationIdAndEvseId(stationId, evseId);
        chargerDomain.updateChargerStatus(ChargerStatus.UNAVAILABLE);
        chargerDomain.addTrouble();
        chargerRepository.updateTroubleAndStatus(chargerDomain);
    }
}