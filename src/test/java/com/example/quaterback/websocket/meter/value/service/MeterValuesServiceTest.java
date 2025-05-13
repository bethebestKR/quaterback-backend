//package com.example.quaterback.websocket.meter.value.service;
//
//import com.example.quaterback.api.domain.station.constant.StationStatus;
//import com.example.quaterback.api.domain.station.entity.ChargingStationEntity;
//import com.example.quaterback.api.domain.station.repository.ChargingStationRepository;
//import com.example.quaterback.api.domain.station.repository.JpaChargingStationRepository;
//import com.example.quaterback.api.domain.station.repository.SpringDataJpaChargingStationRepository;
//import com.example.quaterback.common.redis.service.RedisMapSessionToStationService;
//import com.example.quaterback.websocket.meter.value.converter.MeterValuesConverter;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.persistence.EntityManager;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.time.LocalDateTime;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//@ActiveProfiles("test")
//class MeterValuesServiceTest {
//
//    @Autowired
//    private SpringDataJpaChargingStationRepository springDataRepository;
//
//    private ChargingStationRepository chargingStationRepository;
//
//    @Autowired
//    private MeterValuesConverter meterValuesConverter;
//
//    @Autowired
//    private EntityManager em;
//
//    private RedisMapSessionToStationService redisServiceMock;
//    private MeterValuesService meterValuesService;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @BeforeEach
//    void setUp() {
//        // ✅ DB 초기화로 중복 stationId 문제 해결
//        springDataRepository.deleteAll();
//
//        redisServiceMock = Mockito.mock(RedisMapSessionToStationService.class);
//        chargingStationRepository = new JpaChargingStationRepository(springDataRepository);
//        meterValuesService = new MeterValuesService(meterValuesConverter, redisServiceMock, chargingStationRepository);
//    }
//
//    @Test
//    @Transactional
//    void updateStationEss_정상작동_테스트() throws Exception {
//        springDataRepository.save(
//                ChargingStationEntity.builder()
//                        .stationId("station-001")
//                        .model("M1")
//                        .vendorId("quarterback")
//                        .latitude(37.55)
//                        .longitude(126.55)
//                        .address("서울")
//                        .updateStatusTimeStamp(LocalDateTime.now())
//                        .stationStatus(StationStatus.ACTIVE)
//                        .essValue(50)
//                        .build()
//        );
//
//        String sessionId = "test-session-id";
//        when(redisServiceMock.getStationId(sessionId)).thenReturn("station-001");
//
//        String json = """
//            [
//              2,
//              "test-message-id",
//              "MeterValues",
//              {
//                "meterValue": [
//                  {
//                    "timestamp": "2025-05-03T17:00:00",
//                    "sampledValue": [
//                      {
//                        "value": 88,
//                        "measurand": "Energy.Active.Import.Register"
//                      }
//                    ]
//                  }
//                ]
//              }
//            ]
//        """;
//
//        JsonNode jsonNode = objectMapper.readTree(json);
//
//        // when
//        String updatedStationId = meterValuesService.updateStationEss(jsonNode, sessionId);
//
//        // then
//        em.flush();
//        em.clear();
//
//        ChargingStationEntity updated = springDataRepository.findByStationId("station-001").get();
//        assertThat(updated.getEssValue()).isEqualTo(88);
//        assertThat(updatedStationId).isEqualTo("station-001");
//    }
//}
