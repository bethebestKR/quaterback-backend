package com.example.quaterback.common.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RedisHeartbeatMonitorService {

    private final RedisTemplate<String, Long> redisTemplate;

    public void updateHeartbeat(String stationId) {
        redisTemplate.opsForValue().set("heartbeat:" + stationId, Instant.now().toEpochMilli());
    }

    public Long getLastHeartbeat(String stationId) {
        return redisTemplate.opsForValue().get("heartbeat:" + stationId);
    }

    public void removeHeartbeat(String stationId) {
        redisTemplate.delete("heartbeat:" + stationId);
    }
}
