package com.example.quaterback.websocket;

import com.example.quaterback.common.redis.service.RedisMapSessionToStationService;
import com.example.quaterback.common.redis.service.RedisHeartbeatMonitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTimeoutService {

    private final RedisMapSessionToStationService redisMapSessionToStationService;
    private final RedisHeartbeatMonitorService redisHeartbeatMonitorService;

    public void refreshTimeout(String sessionId) {
        String stationId = redisMapSessionToStationService.getStationId(sessionId);
        if (stationId != null) {
            redisHeartbeatMonitorService.updateHeartbeat(stationId);
        }
    }
}
