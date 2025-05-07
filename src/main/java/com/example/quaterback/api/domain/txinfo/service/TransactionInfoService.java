package com.example.quaterback.api.domain.txinfo.service;

import com.example.quaterback.api.domain.charger.entity.ChargerEntity;
import com.example.quaterback.api.domain.charger.repository.ChargerRepository;
import com.example.quaterback.api.domain.charger.repository.SpringDataJpaChargerRepository;
import com.example.quaterback.api.domain.station.repository.ChargingStationRepository;
import com.example.quaterback.api.domain.txinfo.domain.TransactionInfoDomain;
import com.example.quaterback.api.domain.txinfo.entity.TransactionInfoEntity;
import com.example.quaterback.api.domain.txinfo.repository.SpringDataJpaTxInfoRepository;
import com.example.quaterback.api.domain.txinfo.repository.TxInfoRepository;
import com.example.quaterback.api.feature.managing.dto.apiRequest.CreateTransactionInfoRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    private final SpringDataJpaTxInfoRepository springDataJpaTxInfoRepository;
    private final SpringDataJpaChargerRepository springDataJpaChargerRepository;

    //charger 별 충전기록 얻기
    public List<TransactionInfoEntity> getChargerTransactionsByStationAndPeriod(LocalDateTime start,
                                                                                LocalDateTime end,
                                                                                String stationName){
        //stationId 얻고
        String stationId = chargingStationRepository.findStationIdByStationName(stationName);

        //stationId로 충전소에 관련된 Charger 객체들 뽑고
        List<ChargerEntity> chargerList = new ArrayList<>(chargerRepository.findByStationID(stationId));

        //for 문을 돌면서 해당 charger Pk를 통해  txInfoRepository로 결과 가져오기
        //혹은 tmp 별로 나눠서 따로 저장하여 사용
        List<TransactionInfoEntity> totalTransactionInfos = new ArrayList<>();
        for(ChargerEntity charger : chargerList){
            List<TransactionInfoEntity> tmp = txInfoRepository.findByChargerPkAndCreatedAtBetween(start,
                    end,
                    charger.getId());
            totalTransactionInfos.addAll(tmp);
        }
        return totalTransactionInfos;
    }



    public Page<TransactionInfoEntity> getStationTransactionsByStationAndPeriod(
            LocalDateTime start, LocalDateTime end, String stationName, Pageable pageable
    ){
        String stationId = chargingStationRepository.findStationIdByStationName(stationName);

        Page<TransactionInfoEntity> transactionInfoEntities = txInfoRepository.findByStationIdAndCreatedAtBetween(
                start, end, stationId, pageable
        );
        return transactionInfoEntities;
    }

    public TransactionInfoEntity getTxInfoByTxId(String transactionId){
        TransactionInfoEntity tXInfoEntity = springDataJpaTxInfoRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new EntityNotFoundException("tx info entity not found"));
        return tXInfoEntity;
    }

    public void saveTxInfo(CreateTransactionInfoRequest request){
        TransactionInfoDomain txInfoDomain = request.toDomain();
        ChargerEntity chargerEntity = springDataJpaChargerRepository.findByStation_StationIdAndEvseId(
               txInfoDomain.getStationId(), txInfoDomain.getEvseId()
       ).orElseThrow(() -> new EntityNotFoundException("entity not found"));

        TransactionInfoEntity txEntity = TransactionInfoEntity.fromTransactionInfoDomain(
                txInfoDomain, chargerEntity
        );

        springDataJpaTxInfoRepository.save(txEntity);
    }
}
