package com.example.quaterback.websocket;

import com.example.quaterback.station.constant.StationStatus;
import com.example.quaterback.redis.service.RedisHeartbeatMonitorService;
import com.example.quaterback.station.domain.ChargingStationDomain;
import com.example.quaterback.station.repository.ChargingStationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class InactiveStationCleaner {

    private final RedisTemplate<String, String> redisTemplate;
    private final ChargingStationRepository chargingStationRepository;
    private final RedisHeartbeatMonitorService heartbeatMonitorService;

    private static final long HEARTBEAT_INTERVAL_MS = 30_000; // 30초

    @Transactional
    @Scheduled(fixedRate = 15_000) // 15초 마다 실행
    public void cleanInactiveStations() {
        Set<String> keys = redisTemplate.keys("session:*");

        //현재 설정되어있는 모든 세션-충전소 매핑들을 대상으로 heart beat interval을 체크한다.
        for (String sessionKey : keys) {
            //String sessionId = sessionKey.replace("session:", ""); <-- sessionId 가 필요할 시 사용
            String stationId = (String) redisTemplate.opsForValue().get(sessionKey);

            Long lastHeartbeat = heartbeatMonitorService.getLastHeartbeat(stationId);
            if (lastHeartbeat == null || isExpired(lastHeartbeat)) {
                //상태 변경
                ChargingStationDomain stationDomain = chargingStationRepository.findByStationId(stationId);
                stationDomain.updateStationStatus(StationStatus.INACTIVE);
                chargingStationRepository.update(stationDomain);

                //Redis 정리
                redisTemplate.delete(sessionKey);
                heartbeatMonitorService.removeHeartbeat(stationId);

            }
        }
    }

    private boolean isExpired(Long lastTimestamp) {
        return Instant.now().toEpochMilli() - lastTimestamp > HEARTBEAT_INTERVAL_MS;
    }
}