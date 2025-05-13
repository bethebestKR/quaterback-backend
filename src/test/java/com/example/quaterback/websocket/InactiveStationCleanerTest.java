//package com.example.quaterback.websocket;
//
//import com.example.quaterback.api.domain.station.constant.StationStatus;
//import com.example.quaterback.api.domain.station.entity.ChargingStationEntity;
//import com.example.quaterback.api.domain.station.repository.SpringDataJpaChargingStationRepository;
//import com.example.quaterback.common.redis.service.RedisHeartbeatMonitorService;
//import com.example.quaterback.common.redis.service.RedisMapSessionToStationService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.time.Instant;
//import java.util.List;
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@ActiveProfiles("test")
//public class InactiveStationCleanerTest {
//
//    @Autowired
//    private InactiveStationService inactiveStationService;
//
//    @Autowired
//    private SpringDataJpaChargingStationRepository stationRepository;
//
//    @Autowired
//    private StringRedisTemplate redisTemplate;
//
//    @Autowired
//    private RedisMapSessionToStationService mapService;
//
//    @Autowired
//    private RedisHeartbeatMonitorService heartbeatMonitorService;
//
//    private final List<String> stationIds = List.of("station-001", "station-002");
//
//    @BeforeEach
//    void setup() {
//        redisTemplate.delete(redisTemplate.keys("sessionId:*"));
//        redisTemplate.delete(redisTemplate.keys("stationId:*"));
//        redisTemplate.delete(redisTemplate.keys("heartbeat:*"));
//
//        stationRepository.deleteAll();
//
//        for (String stationId : stationIds) {
//            ChargingStationEntity station = ChargingStationEntity.builder()
//                    .stationId(stationId)
//                    .stationStatus(StationStatus.ACTIVE)
//                    .model("model")
//                    .vendorId("vendor")
//                    .latitude(0.0)
//                    .longitude(0.0)
//                    .address("some address")
//                    .build();
//
//            stationRepository.save(station);
//            mapService.mapSessionToStation("session-" + stationId, stationId);
//            heartbeatMonitorService.updateHeartbeat(stationId);
//        }
//
//        // 일부 충전소를 타임아웃 시킴
//        heartbeatMonitorService.updateHeartbeat("station-001"); // 최신
//        redisTemplate.opsForValue().set("heartbeat:station-002", String.valueOf(Instant.now().minusMillis(31_000).toEpochMilli())); // 오래됨
//    }
//
//    @Test
//    void 만료된_충전소_비활성화_성공() {
//        inactiveStationService.cleanInactiveStationsWithTx();
//
//        // station-001은 유지
//        ChargingStationEntity activeStation = stationRepository.findByStationId("station-001").orElseThrow();
//        assertThat(activeStation.getStationStatus()).isEqualTo(StationStatus.ACTIVE);
//
//        // station-002는 비활성화
//        ChargingStationEntity inactiveStation = stationRepository.findByStationId("station-002").orElseThrow();
//        assertThat(inactiveStation.getStationStatus()).isEqualTo(StationStatus.INACTIVE);
//
//        // Redis에서 매핑 삭제 확인
//        assertThat(redisTemplate.hasKey("sessionId:session-station-002")).isFalse();
//        assertThat(redisTemplate.hasKey("stationId:station-002")).isFalse();
//        assertThat(redisTemplate.hasKey("heartbeat:station-002")).isFalse();
//    }
//}
